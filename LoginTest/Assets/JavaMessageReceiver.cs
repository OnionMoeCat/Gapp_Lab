using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class JavaMessageReceiver : MonoBehaviour {

    public Text text;

    void JavaMessage(string message)
    {
        text.text = message;
    }
}
