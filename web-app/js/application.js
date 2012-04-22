/**
 * Setup goes here
 */

    // TODO move this to above after 0.5.3
_.extend(Backbone.Validation.callbacks, {
    valid:function (view, attr, selector) {
        // TODO this is always passing the default ("name") for the selector instead of the one configured above
        selector = 'id';
        var cg = view.$('[' + selector + '~=' + attr + ']').parents('.control-group');
        cg.removeClass('error');
        $('.error-text', cg).hide();
    },

    invalid:function (view, attr, error, selector) {
        // TODO this is always passing the default ("name") for the selector instead of the one configured above
        selector = 'id';
        var cg = view.$('[' + selector + '~=' + attr + ']').parents('.control-group');
        cg.addClass('error');
        $('.error-text', cg).text(error).show();
    }
});

/**
 * The main application
 */
var App = window.App = {};
if(document.URL.indexOf('/goal.ly/') > 0) {
    App.baseUrl = '/goal.ly/'
} else {
    App.baseUrl = '/'
}

/*
 * Models
 */
App.QuestionModel = Backbone.Model.extend({
    url:App.baseUrl + 'question',

    defaults:{
        id:undefined,
        questionType:'Boolean',
        responses:[],
        text:undefined,
        time:20
    },

    isNew:function () {
        var _id = this.get('id');
        return _id == undefined || _id == null;
    },

    reset:function () {
        this.set(this.defaults, {silent:true});
    },

    validation:{
        questionType:{
            required:true,
            msg:'A question type is required'
        },
        text:{
            required:true,
            msg:'A question is required'
        }, time:{
            required:true,
            range:[0,23],
            msg:'A time is required'
        }
    }
});

App.QuestionList = Backbone.Collection.extend({
    model:App.QuestionModel,
    url:App.baseUrl + 'question'
});

App.StateModel = Backbone.Model.extend({
    defaults:{
        hasQuestions:false
    },

    initialize:function () {
        this.questions = new App.QuestionList();
        this.questions.on('add remove reset', this.questionCount, this);
    },

    questionCount:function () {
        var qc = this.questions.size();
        if (qc > 0) {
            this.set('hasQuestions', true);
        } else {
            this.set('hasQuestions', false);
        }
    }
});
App.state = new App.StateModel();

/*
 * Views
 */
App.QuestionView = Backbone.View.extend({
    tagName:'li',

    template:_.template($('#question-template').html()),
    /**
     * Model is an App.QuestionModel
     */
    model:null,

    events:{
        'click .question-edit':'editQuestion',
        'click .question-remove':'removeQuestion'
    },

    /**
     * This will add or update based on if the model has already been saved (determined by id)
     */
    editQuestion:function () {
        this.model.save({}, {success:this.saved});
    },

    removeQuestion:function () {
        this.model.destroy({data:JSON.stringify(this.model.toJSON()), success:_.bind(this.remove, this)});
    },

    saved:function (model) {
        if (!App.state.questions.any(function (m) {
            return m.get('id') == model.get('id');
        })) {
            App.state.questions.add(model);
        }
    },

    render:function () {
        this.$el.html(this.template(this.model.toJSON()));

        Backbone.Validation.bind(this, {forceUpdate: true});
        Backbone.ModelBinding.bind(this);

        // Wait until the chart api has been loaded
        App.chartLoaded.done(_.bind(function () {
            var responses = this.model.get('responses');
            if(!responses || responses.length < 1) {
                return;
            }

            var chartDiv = this.$('.chart');
            chartDiv.show();

            responses = _.map(responses, function(response) {
                var date = new Date(response.date);
                var d = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
                return [d, response.value];
            });

            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Date');
            data.addColumn('number', 'Value');
            data.addRows(responses);

            var chart = new google.visualization.LineChart(chartDiv[0]);
            chart.draw(data, {title: 'History'});
        }, this));

        return this;
    }
});

App.QuestionListView = Backbone.View.extend({
    events:{
        'click #add-question':'addNewQuestion'
    },

    addNewQuestion:function (event) {
        event.preventDefault();
        if (!this.addView.model.isValid(true)) {
            return;
        }

        var self = this;
        this.addView.model.save({}, {success:function (model, response) {
            self.saved(model, response);
        }});
    },

    saved:function (model) {
        if (!App.state.questions.any(function (m) {
            return m.get('id') == model.get('id');
        })) {
            App.state.questions.add(model);
            this.add(model);
            Backbone.ModelBinding.unbind(this.addView);
            Backbone.Validation.unbind(this.addView);

            this.addView.$('#text').val('');
            this.addView.model = new App.QuestionModel();
            Backbone.Validation.bind(this.addView);
            Backbone.ModelBinding.bind(this.addView);
        }
    },

    initialize:function () {
        this.addView = new Backbone.View({ el:this.$('#add-question-view'),
            model:new App.QuestionModel()
        });
        Backbone.Validation.bind(this.addView);
        Backbone.ModelBinding.bind(this.addView);
        App.state.questions.on('reset', this.reset, this);
    },

    add:function (questionModel) {
        var question = new App.QuestionView({model:questionModel});
        this.$el.find('ul').append(question.render().el);
    },

    reset:function () {
        App.state.questions.each(this.add, this);
    }
});

App.MainView = Backbone.View.extend({
    initialize:function () {
        Backbone.ModelBinding.bind(this);

        new App.QuestionListView({
            el:$('#question-list'),
            model:App.state.questions
        });
    }
});
App.main = new App.MainView({
    el:$('#main'),
    model:App.state
});

/*
 * Application Router
 */
App.Router = Backbone.Router.extend({
    routes:{
        'list':'list'
    },

    list:function () {
        App.state.set('section', 'list');
    }
});
App.router = new App.Router();

// Start the router
if (!Backbone.history.start()) {
    App.router.navigate('list', {trigger:true});
}

App.chartLoaded = new $.Deferred();
google.load("visualization", "1", {packages:["corechart"], callback:function() {
   App.chartLoaded.resolve();
}});
