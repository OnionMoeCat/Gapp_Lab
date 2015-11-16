using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class Click : MonoBehaviour {

#if UNITY_ANDROID && !UNITY_EDITOR
    private static string fullClassName = "com.tejas.login.LoginManager";
    private static string unityClass = "com.unity3d.player.UnityPlayerNativeActivity";
#endif

    public InputField username;
    public InputField password;

    public void Clicked()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass pluginClass = new AndroidJavaClass(fullClassName);
        if (pluginClass != null)
        {
            pluginClass.CallStatic("Login", unityClass, username.text, password.text);
        }
#endif
    }
}
