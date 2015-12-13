package fr.bragabresolin.menhir.Core.Message;

public class Message
{
    private MessageType type;
    private Object body;

    public Message(MessageType type, Object body) {
        this.type = type;
        this.body = body;
    }

	public Message(MessageType type) {
		this.type = type;
		this.body = null;
	}

    public MessageType getType() {
        return type;
    }

    public Object getBody() {
        return body;
    }
}

