using AnnHome.Entity;
using AnnHome.Http;
using AnnHome.Tools;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace AnnHome
{
    /// <summary>
    /// Interaction logic for Cards.xaml
    /// </summary>
    public partial class Articles : UserControl
    {
        private Datas datas = null;
        private int pages = 0;


        public Articles()
        {
            InitializeComponent();
            init();
                   }
        private void Window_ContentRendered(object sender, EventArgs e)
        {
            //this.wbrExam.ObjectForScripting = new OprateBasic(this);
            // this.wbrExam.NavigateToString("<HTML>< head ><mce:script language =\"JavaScript\" type = \"text/javascript\"></mce:script> </ head >< Body>< Form>< div id = \"div1\" onClick = \"Selec();\"> 000000000000 </ div> < div id = \"div2\"> 111111 </ div></ Form></ Body></ HTML>");
            this.wbrExam.InvokeScript("Selec");
        }

       
        private void init()
        {
            int code = 0;
            //显示最新文章
            string result = HttpHelper.RequestUrl(Contacts.getAllArticles(), out code);
            
            a_content.Navigating += a_content_Navigating;

            if (code != -1)
            {
                JavaScriptSerializer js = new JavaScriptSerializer();
                datas = js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                if (datas?.Count > 0)
                {
                    Posts posts = datas?.Posts[0];
                    a_pic.Source = ToolHelper.showNetImage(posts?.Attachments?[0].Url);
                    a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
                    a_author.Content = "by : "+posts?.Author?.Nickname;
                    a_title.Content = posts?.Title;
                    a_time.Content = posts?.Date;
                    a_url.Content = posts?.Url;
                    
                }
            }
        }

        /// <summary>  
        /// 设置浏览器静默，不弹错误提示框  
        /// </summary>  
        /// <param name="webBrowser">要设置的WebBrowser控件浏览器</param>  
        /// <param name="silent">是否静默</param>  
        private void SetWebBrowserSilent(WebBrowser webBrowser, bool silent)
        {
            FieldInfo fi = typeof(WebBrowser).GetField("_axIWebBrowser2", BindingFlags.Instance | BindingFlags.NonPublic);
            if (fi != null)
            {
                object browser = fi.GetValue(webBrowser);
                if (browser != null)
                    browser.GetType().InvokeMember("Silent", BindingFlags.SetProperty, null, browser, new object[] { silent });
            }
        }

        void a_content_Navigating(object sender, NavigatingCancelEventArgs e)
        {
            SetWebBrowserSilent(sender as WebBrowser, true);
        }

        private void btn_change_Click(object sender, RoutedEventArgs e)
        {
            if (pages < datas?.Posts.Count-1)
            {
                pages++;
                Posts posts = datas?.Posts[pages];
                if(posts?.Attachments.Count>0)
                    a_pic.Source = ToolHelper.showNetImage(posts?.Attachments?[0].Url);
                a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
                a_author.Content = "by : " + posts?.Author?.Nickname;
                a_title.Content = posts?.Title;
                a_time.Content = posts?.Date;
                a_url.Content = posts?.Url;
            }
            else
            {
                pages = 0;
                Posts posts = datas?.Posts[pages];
                a_pic.Source = ToolHelper.showNetImage(posts?.Attachments?[0].Url);
                a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
                a_author.Content = "by : " + posts?.Author?.Nickname;
                a_title.Content = posts?.Title;
                a_time.Content = posts?.Date;
                a_url.Content = posts?.Url;
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            
        }
    }
}
