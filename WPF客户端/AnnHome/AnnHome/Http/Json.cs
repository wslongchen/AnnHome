using AnnHome.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;

namespace AnnHome.Http
{
    class Json
    {
        /// <summary>
        /// 通过网页信息获取数据
        /// </summary>
        /// <param name="htmlurl"></param>
        /// <param name="encoding"></param>
        /// <returns></returns>
        public static JsonObject GetJsonByHtml(string htmlurl, Encoding encoding)
        {
            WebClient MyWebClient = new WebClient();
            MyWebClient.Credentials = CredentialCache.DefaultCredentials;//获取或设置用于向Internet资源的请求进行身份验证的网络凭据
            //Byte[] pageData = MyWebClient.DownloadData(@"http://www.autohome.com.cn/ashx/AjaxIndexCarFind.ashx?type=1"); //从指定网站下载数据
            Byte[] pageData = MyWebClient.DownloadData(htmlurl); //从指定网站下载数据
            string pageHtml = encoding.GetString(pageData); //如果获取网站页面采用的是GB2312，则使用这句 
            JsonObject JsonObjects = JsonObject.Parse(pageHtml);//将页面信息转换成数据
            return JsonObjects;
        }
    }

   
    #region 解析JSON字串
    public class JsonObject
    { /// <summary>
      /// 解析JSON字串
      /// </summary>
        public static JsonObject Parse(string json)
        {
            JavaScriptSerializer js = new JavaScriptSerializer();


            object obj = js.DeserializeObject(formatString(json));
            
            return new JsonObject
            {
                Value = obj
            };
        }
        public static string formatString(String s)
        {
            if (s != null)
            {
                s = s.Replace("\ufeff", "");
            }
            return s;
        }

        /// <summary>
        /// 取对象的属性
        /// </summary>
        public JsonObject this[string key]
        {
            get
            {
                Dictionary<string, object> dict = Value as Dictionary<string, object>;
                if (dict != null && dict.ContainsKey(key))
                {
                    return new JsonObject { Value = dict[key] };
                }


                return new JsonObject();
            }


        }


        /// <summary>
        /// 取数组
        /// </summary>
        public JsonObject this[int index]
        {
            get
            {
                object[] array = Value as object[];
                if (array != null && array.Length > index)
                {
                    return new JsonObject { Value = array[index] };
                }
                return new JsonObject();
            }
        }


        /// <summary>
        /// 将值以希望类型取出
        /// </summary>
        public T GetValue<T>()
        {
            return (T)Convert.ChangeType(Value, typeof(T));
        }


        /// <summary>
        /// 取出字串类型的值
        /// </summary>
        public string Text()
        {
            return Convert.ToString(Value);
        }


        /// <summary>
        /// 取出数值
        /// </summary>
        public double Number()
        {
            return Convert.ToDouble(Value);
        }


        /// <summary>
        /// 取出整型
        /// </summary>
        public int Integer()
        {
            return Convert.ToInt32(Value);
        }


        /// <summary>
        /// 取出布尔型
        /// </summary>
        public bool Boolean()
        {
            return Convert.ToBoolean(Value);
        }


        /// <summary>
        /// 值
        /// </summary>
        public object Value
        {
            get;
            set;
        }


        /// <summary>
        /// 如果是数组返回数组长度
        /// </summary>
        public int Length
        {
            get
            {
                object[] array = Value as object[];
                if (array != null)
                {
                    return array.Length;
                }
                return 0;
            }
        }


    }
    #endregion
}