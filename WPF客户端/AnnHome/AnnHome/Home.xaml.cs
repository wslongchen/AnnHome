using AnnHome.Entity;
using AnnHome.Http;
using AnnHome.Tools;
using System;
using System.Diagnostics;
using System.Globalization;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Imaging;

namespace AnnHome
{

    /// <summary>
    /// Interaction logic for Home.xaml
    /// </summary>
    public partial class Home : UserControl
    {

        public Home()
        {
            InitializeComponent();
            
            init();
            
        }

        private void GitHubButton_OnClick(object sender, RoutedEventArgs e)
        {
            Process.Start("https://github.com/wslongchen");
        }

        private void TecentButton_OnClick(object sender, RoutedEventArgs e)
        {
            Process.Start("http://sighttp.qq.com/authd?IDKEY=b9193abd2e31d581ddfbee615bfc56153cff7fc762ac34cd");
        }

        private void AnnButton_OnClick(object sender, RoutedEventArgs e)
        {
            Process.Start("https://www.mrpann.com");
        }

        private void EmailButton_OnClick(object sender, RoutedEventArgs e)
        {
            Process.Start("mailto://longchen@mrpann.com");
        }

        //初始化数据
        private void init()
        {
            //初始化控件属性
            this.img_weather.ToolTip="当前地址："+ ToolHelper.getCurrentPlace();
            int code = 0,date_code=0;
            string currentDate = DateTime.Now.ToString("yyyy-MM-dd");
            this.index_date.Content = currentDate;
            this.index_date.ToolTip =DateTime.Now.ToString("dddd", new CultureInfo("zh-cn"));
            string holiday= ToolHelper.CheckHoliday(out date_code);
            switch(date_code)
            {
                case 0:
                    this.index_holiday.Content = holiday;
                    break;
                case 1:
                    this.index_holiday.Content = holiday+"(放假)";
                    break;
                case -1:
                    this.date_card.Width =180;
                    this.border.Visibility = Visibility.Hidden;
                    break;
                default:
                    break;
            }
            //显示最新文章
            string result = HttpHelper.RequestUrl(Contacts.getAllArticles(), out code);
            //string data= "{\"status\":\"publish\",\"title\":\"sdfadafsasdf\",\"content\":\"dasdfasdfasdfasdfasdfsfa\",\"author\":\"vaelongchen\",\"categories\":[],\"tags\":[]}";
            //string result2 = HttpHelper.RequestUrl("http://www.mrpann.com/?json=create_posts", out code,data,"POST");
            if (code != -1)
            {
                JavaScriptSerializer js = new JavaScriptSerializer();
                Datas datas=js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                if (datas?.Count > 0)
                {
                    Posts posts = datas?.Posts[0];
                    this.index_title.Content = posts?.Title.ToString().Trim();
                    this.index_content.Text = HttpHelper.FiltHtmlCode(posts?.Excerpt.ToString().Trim());
                }
            }
        }

        private void index_title_MouseLeftButtonDown(object sender, System.Windows.Input.MouseButtonEventArgs e)
        {
            //Cards c = new Cards();
            //var w = new Window();
            //w.Content = c;
            //w.WindowStartupLocation = WindowStartupLocation.CenterScreen;
            //w.WindowStyle = WindowStyle.SingleBorderWindow;
            //w.Width = 210;
            //w.Height = 300;
            //w.ShowDialog();
        }
    }
}
