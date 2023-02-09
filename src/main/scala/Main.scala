import com.slack.api.bolt.App
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import com.slack.api.bolt.socket_mode.SocketModeApp
import com.slack.api.methods.SlackApiException
import com.slack.api.methods.request.conversations.ConversationsHistoryRequest
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse
import com.slack.api.model.Message
import com.slack.api.model.Reaction
import com.slack.api.Slack
import com.slack.api.methods.request.reactions.ReactionsGetRequest
import com.slack.api.model.event.ReactionAddedEvent

import java.util
import java.util.Optional
import java.time.LocalDateTime
import java.time.ZoneId

import  scala.jdk.CollectionConverters._

object Main {
  def main(args: Array[String]): Unit = {
    val app = new App()
    var conversationList: Optional[util.List[Message]] = Optional.empty
    val time = LocalDateTime.of(2023, 2, 6, 0, 0, 0)

    app.command("/hello", (req, ctx) => {
      val result = ctx.client().conversationsHistory((r: ConversationsHistoryRequest.ConversationsHistoryRequestBuilder) => r
        .token(System.getenv("SLACK_BOT_TOKEN"))
        .channel("C04MA9CCUD6")
        .inclusive(false)
        .oldest(time.atZone(ZoneId.systemDefault()).toEpochSecond.toString)
      )
      println(result.getMessages)
      val messageList = result.getMessages
      conversationList = Optional.ofNullable(messageList)
      conversationList.map( msg => msg.removeIf(m => m.getReactions == null))

      var set = Set("")
      conversationList.map( msg =>
        msg.forEach( m =>
          m.getReactions.forEach( res =>
            set += s"<@${m.getUser}>さん：\n:${res.getName}:：${res.getCount}個\n"
          )
        )
      )
      ctx.ack(set.mkString("スタンプ数を発表します！\n","",""))
//      ctx.ack(res => res.responseType("in_channel").text(":persimmon: 柿をどうぞ！"))
    })
    
    app.event(classOf[ReactionAddedEvent], (payload, ctx) => {
      println(payload)
      ctx.ack()
    })

    new SocketModeApp(app).start()
    println("success")
  }
}
