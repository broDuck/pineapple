<%@ page import="com.rabbitmq.client.*" %>
<%@ page import="java.io.IOException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="utf-8" />
<!-- Website Design By: www.happyworm.com -->
<title>파인애플 플레이어</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/resources/dist/skin/blue.monday/css/jplayer.blue.monday.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/resources/dist/lib/jquery.min.js"></script>
<script type="text/javascript" src="/resources/dist/jplayer/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="/resources/dist/add-on/jplayer.playlist.min.js"></script>
<script type="text/javascript">
//<![CDATA[

var addInfo;

$(document).ready(function(){

	var myPlaylist = new jPlayerPlaylist({
		jPlayer: "#jquery_jplayer_1",
		cssSelectorAncestor: "#jp_container_1"
	},
	[{
		title : "마음대로",
		artist : "이진아",
		mp3 : "C:/pineapple/music/2016/01/26/c39f475c-14b5-46b8-a5cc-4e48ef7bda2c_099-이진아-01-마음대로.mp3"
	}],
	{
		playlistOptions: {
			autoPlay: true
		},
		swfPath: "/resources/dist/jplayer",
		supplied: "mp3",
		wmode: "window",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true
	});

	console.log('${login.id}');

	<%
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

		channel.queueDeclare("test", false, false, false, null);
		System.out.println("[*] client successful connection!");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println("<script language=javascript>");
				System.out.print("addPlaylist(");
				System.out.println(message + ");");
				System.out.println("</script>");
			}
		};
		channel.basicConsume("test", true, consumer);
	%>

	function addPlaylist(message) {
		myPlaylist.add(message);
		console.log(message);
	}
});
//]]>
</script>
</head>
<body>
<div id="jquery_jplayer_1" class="jp-jplayer"></div>
<div id="jp_container_1" class="jp-audio" role="application" aria-label="media player">
	<div class="jp-type-playlist">
		<div class="jp-gui jp-interface">
			<div class="jp-controls">
				<button class="jp-previous" role="button" tabindex="0">previous</button>
				<button class="jp-play" role="button" tabindex="0">play</button>
				<button class="jp-next" role="button" tabindex="0">next</button>
				<button class="jp-stop" role="button" tabindex="0">stop</button>
			</div>
			<div class="jp-progress">
				<div class="jp-seek-bar">
					<div class="jp-play-bar"></div>
				</div>
			</div>
			<div class="jp-volume-controls">
				<button class="jp-mute" role="button" tabindex="0">mute</button>
				<button class="jp-volume-max" role="button" tabindex="0">max volume</button>
				<div class="jp-volume-bar">
					<div class="jp-volume-bar-value"></div>
				</div>
			</div>
			<div class="jp-time-holder">
				<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
				<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
			</div>
			<div class="jp-toggles">
				<button class="jp-repeat" role="button" tabindex="0">repeat</button>
				<button class="jp-shuffle" role="button" tabindex="0">shuffle</button>
			</div>
		</div>
		<div class="jp-playlist">
			<ul>
				<li>&nbsp;</li>
			</ul>
		</div>
		<div class="jp-no-solution">
			<span>Update Required</span>
			To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
		</div>
	</div>
</div>
</body>

</html>
