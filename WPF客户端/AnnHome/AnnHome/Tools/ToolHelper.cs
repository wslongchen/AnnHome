using AnnHome.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Media.Imaging;

namespace AnnHome.Tools
{
    class ToolHelper
    {
        public static string ConvertExtendedASCII(string HTML)
        {
            string retVal = "";
            char[] s = HTML.ToCharArray();

            foreach (char c in s)
            {
                if (Convert.ToInt32(c) > 127)
                    retVal += "&#" + Convert.ToInt32(c) + ";";
                else
                    retVal += c;
            }

            return retVal;
        }
        //检查是否是节日
        public static string CheckHoliday(out int code)
        {
            string date = DateTime.Now.ToString("MMdd");
            code = 0;
            var result = sFtv.Where(s => s.Contains(date));
            if (result.Count() > 0)
            {
                string festival = result.First().Replace(date, "").Trim();
                if (festival.Contains("*"))
                {
                    code = 1;
                    return festival.Replace("*", "").Trim();
                }
                else
                {
                    return festival;
                }
            }
            else
                code = -1;
            
            return "";
        }
        //获取当前地址
        public static string getCurrentPlace()
        {
            int code = 0;
            string result = HttpHelper.RequestUrl("http://int.dpool.sina.com.cn/iplookup/iplookup.php", out code);
            if (code != -1)
            {
                var place = result?.Split('\t');
                return place?[5];
            }
            return "";

        }

        //显示网络照片
        public static BitmapImage showNetImage(string url)
        {
            if (url != null)
            {
                BitmapImage myBitmapImage = new BitmapImage();
                myBitmapImage.BeginInit();
                myBitmapImage.UriSource = new Uri(url, UriKind.Absolute);
                myBitmapImage.DecodePixelWidth = 2048;
                myBitmapImage.EndInit();
                return myBitmapImage;
            }
            return null;
        }
        //公历节日 *表示放假日
        static string[] sFtv = new string[]{
        "0101*元旦",
        "0210 气象节",
        "0214 情人节",
        "0303 爱耳日",
        "0308 妇女节",
        "0312 植树节",
        "0314 国际警察节",
        "0315 国际消费者权益日",
        "0323 世界气象日",
        "0401 愚人节",
        "0422 地球日",
        "0501 劳动节",
        "0512 护士节",
        "0515 国际家庭日",
        "0519 中国旅游日",
        "0504 青年节",
        "0531 无烟日",
        "0601 儿童节",
        "0605 世界环境日",
        "0626 国际反毒品日",
        "0606 爱眼日",
        "0701 建党日",
        "0707 七七事变",
        "0711 中国航海日",
        "0801 建军节",
        "0815 日本投降日",
        "0903 抗战胜利日",
        "0920 爱牙日",
        "0930 烈士纪念日",
        "0910 教师节",
        "0918 九·一八事变纪念日",
        "1001*国庆节",
        "1031 万圣节",
        "1016 世界粮食日",
        "1108 记者节",
        "1111 光棍节",
        "1117 国际大学生节",
        "1201 艾滋病日",
        "1210 世界人权日",
        "1213 南京大屠杀纪念日",
        "1220 澳门回归纪念日",
        "1224 平安夜",
        "1225 圣诞节",
        "0520 母亲节",
        "0630 父亲节",
        "1144 感恩节",
        "0101*春节",
        "0115 元宵节",
        "0202 龙抬头",
        "0505 端午节",
        "0707 七夕",
        "0715 中元节",
        "0815 中秋节",
        "0909 重阳节",
        "1208 腊八节",
        "1223 北方小年",
        "1224 南方小年",
        "0100*除夕" };
        //调用JS
        //        Basic ds = new Basic();
        //        this.wbrExam.NavigateToString("<HTML><head><script language =\"JavaScript\" type = \"text/javascript\">function Selec(){alert(\"提示信息！\"); }</script> </head><Body><Form><div id = \"div1\" onClick = \"Selec();\"> 000000000000 </div> <div id = \"div2\"> 111111 </ div></Form></Body></HTML>");
        //        wbrExam.ObjectForScripting = ds;//该对象可由显示在WebBrowser控件中的网页所包含的脚本代码访问  

        //        this.wbrExam.InvokeScript("Selec");

        //         [System.Runtime.InteropServices.ComVisibleAttribute(true)]//将该类设置为com可访问  
        //    public class Basic
        //    {
        //        public static string name;
        //        public string Name
        //        {
        //            get { return name; }
        //            set { name = value; }
        //        }
        //        public void ClickEvent(string str)
        //        {
        //            this.Name = str;
        //        }
        //    }

    }
}
