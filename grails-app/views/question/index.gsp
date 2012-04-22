<%@ page import="grails.converters.JSON" %>
<!doctype html>
<html>
<head>
	<meta name="layout" content="onepage"/>
	<title>Easy tracking</title>
</head>

<body>

<div id="main" class="fluid-row">
	<p id="no-questions" data-bind="hidden hasQuestions">
		Text about adding questions goes here.
	</p>

	<div id="question-list" data-bind="visible hasQuestions">
		<div id="add-question-view" class="control-group">
			<div class="controls">
				Question Type: <g:select id="questionType" name="questionType" from="${ly.goal.QuestionType.values()}" />
				Ask Time: <g:select id="time" name="time" from="${times}" optionKey="key" optionValue="value" />
				<input type="text" id="text" class="input-xxlarge"
					   placeholder="Type the question that you would like goal.ly to ask you every day.">
				<a id="add-question" href="#" class="btn question-add"><i class="icon-plus"></i> Add</a>
			</div>
		</div>
		<ul></ul>
	</div>
</div>

<h:script_template id="question-template">
	<div class="control-group">
		<div class="controls">
			Ask Time: <g:select id="time" name="time" from="${times}" optionKey="key" optionValue="value" />
			<input type="text" id="text" class="input-xxlarge">
			<a href="#" class="btn question-edit"><i class="icon-plus"></i> Edit</a>
			&nbsp;
			<a href="#" class="btn btn-danger question-remove"><i class="icon-trash icon-white"></i> Remove</a>

			<p class="help-block error-text hide"></p>
		</div>
	</div>

	<div class="chart hide" style="width: 600px; height: 400px;"></div>
</h:script_template>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<r:require module='application'/>
<r:script>
		App.state.questions.reset(<%=questions as JSON%>)
</r:script>

</body>
</html>
