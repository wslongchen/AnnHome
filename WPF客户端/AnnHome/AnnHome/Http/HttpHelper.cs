using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace AnnHome.Http
{
    internal class HttpHelper
    {
        private static CookieContainer cookieContainer = new CookieContainer();
        private static String userAgent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; TencentTraveler 4.0; EmbeddedWB 14.52 from: http://www.bsalsa.com/ EmbeddedWB 14.52; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET4.0C; .NET4.0E; InfoPath.3; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)";
        private static String accept = "*/*";
        private static String contentType = "application/x-www-form-urlencoded; charset=UTF-8";
        private static Dictionary<string, string> m_cookies = new Dictionary<string, string>();
        
        /// <summary>
        /// 过滤网页代码
        /// </summary>
        /// <param name="html"></param>
        /// <returns></returns>
        public static string FiltHtmlCode(string html)
        {
            MatchCollection mc = Regex.Matches(html, @"(?<=<(?!a)[^>]*>|^)(?!\s+<)[^<>]+(?=<(?!/a)[^>]*>|$)", RegexOptions.IgnoreCase);
            string result = "";
            foreach (Match m in mc)
            {
                result += m.Value;
            }
            return result;
        }

        /// <summary>
        /// Json数据去bom报头
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        public static string formatJsonString(String s)
        {
            if (s != null)
            {
                s = s.Replace("\ufeff", "");
            }
            return s;
        }

        /// <summary>
        /// http get请求
        /// </summary>
        /// <param name="url"></param>
        /// <param name="data"></param>
        /// <param name="method"></param>
        /// <param name="contentType"></param>
        /// <param name="charset"></param>
        /// <returns></returns>
        public static string RequestUrl(string url, out int requestCode, string data = null, string method = "GET", string contentType = "text", string charset = "utf-8")
        {
            try
            {
                requestCode = 1;
                var request = WebRequest.Create(url);
                request.Method = method;
                request.ContentType = contentType;
                request.Headers.Add("charset:" + charset);
                var encoding = Encoding.GetEncoding(charset);

                if (data != null)
                {
                    byte[] buffer = encoding.GetBytes(data);
                    request.ContentLength = buffer.Length;
                    request.GetRequestStream().Write(buffer, 0, buffer.Length);
                }
                else
                {
                    request.ContentLength = 0;
                }

                using (HttpWebResponse wr = request.GetResponse() as HttpWebResponse)
                {
                    using (StreamReader reader = new StreamReader(wr.GetResponseStream(), encoding))
                    {
                        return reader.ReadToEnd();
                    }
                }
            }
            catch (WebException ex)
            {
                requestCode = -1;
                return ex.Message;
            }
        }

        #region http
        /// <summary>
        /// 请求http，获得响应
        /// </summary>
        /// <param name="url">http地址</param>
        /// <param name="data">要发送的数据</param>
        /// <returns></returns>
        internal static HttpWebResponse GetResponse(string url, byte[] data = null)
        {
            var request = (HttpWebRequest)WebRequest.Create(url);
            request.ContentType = "application/x-www-form-urlencoded; charset=UTF-8";
            request.UserAgent = userAgent;
            request.Accept = accept;
            request.ContentType = contentType;
            request.Referer = url;
            request.CookieContainer = cookieContainer;

            if (data != null)
            {
                request.Method = "POST";
                request.ContentLength = data.Length;
                using (var stream = request.GetRequestStream())
                {
                    stream.Write(data, 0, data.Length);
                }
            }
            else
            {
                request.Method = "GET";
            }

            return (HttpWebResponse)request.GetResponse();
        }

        /// <summary>
        /// 从响应获得字符串
        /// </summary>
        /// <param name="url"></param>
        /// <param name="data"></param>
        /// <returns></returns>
        internal static string GetHtml(string url, byte[] data = null)
        {
            using (var response = GetResponse(url, data))
            {
                ProcessCookies(response.Cookies);

                using (var stream = response.GetResponseStream())
                {
                    using (var sr = new StreamReader(stream, Encoding.UTF8))
                    {
                        return sr.ReadToEnd();
                    }
                }
            }
        }

        /// <summary>
        /// 从响应获得图像
        /// </summary>
        /// <param name="url"></param>
        /// <returns></returns>
        internal static Image GetImage(string url)
        {
            using (var response = GetResponse(url))
            {
                ProcessCookies(response.Cookies);

                using (var stream = response.GetResponseStream())
                {
                    return Image.FromStream(stream);
                }
            }
        }

        /// <summary>
        /// 获取指定键的cookies值
        /// </summary>
        /// <param name="key"></param>
        /// <returns></returns>
        internal static string GetCookie(string key)
        {
            if (!m_cookies.ContainsKey(key))
            {
                return string.Empty;
            }
            return m_cookies[key];
        }

        /// <summary>
        /// 处理响应的cookies
        /// </summary>
        /// <param name="cookies"></param>
        private static void ProcessCookies(CookieCollection cookies)
        {
            foreach (Cookie cookie in cookies)
            {
                if (m_cookies.ContainsKey(cookie.Name))
                {
                    m_cookies[cookie.Name] = cookie.Value;
                }
                else
                {
                    m_cookies.Add(cookie.Name, cookie.Value);
                }
                cookieContainer.Add(cookie);
            }
        }
        #endregion
    }
}

