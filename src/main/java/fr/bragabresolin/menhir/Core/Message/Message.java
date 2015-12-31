package fr.bragabresolin.menhir.Core.Message;

/**
 * Classe représentant un message émis par les classes émetrices (observables) 
 * du jeu afin de transmettre plus d'information à leurs observateurs.
 * Un message est composé d'un type défini depuis une énumération, et d'un corps
 * quelconque. Le type concret du corps dépend généralement du type et n'est pas 
 * imposé à la compilation. On veillera donc à utiliser sciemment ce membre afin
 * de ne pas provoquer d'erreur à l'exécution liée à un problème de type.
 *
 * @author  Logan Braga
 * @author  Simon Bresolin
 * @see fr.bragabresolin.menhir.Core.Message.MessageType
 */
public class Message {

	/**
	 * Représente le type du message envoyé.
	 *
	 * Une valeur nulle est possible, mais devra être gérée en conséquence par 
	 * le receveur du message.
	 * 
	 * @see fr.bragabresolin.menhir.Core.Message.MessageType
	 */
    private MessageType type;

	/**
	 * Représente le corps, quelconque, du message.
	 *
	 * Une valeur nulle est possible, mais devra être gérée en
	 * conséquence par le receveur du message.
	 */
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
