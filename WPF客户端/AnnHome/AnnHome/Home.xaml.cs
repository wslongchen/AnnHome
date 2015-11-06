using AnnHome.Entity;
using AnnHome.Http;
using AnnHome.Tools;
using System;
using System.Diagnostics;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Controls;


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
            int code = 0,date_code=0;
            string currentDate = DateTime.Now.ToString("yyyy-MM-dd");
            string holiday= DateHelper.CheckHoliday(out date_code);
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
            string result = HttpHelper.RequestUrl(Contacts.getAllArticles(), out code);
            if (code != -1)
            {
                JavaScriptSerializer js = new JavaScriptSerializer();
                Datas datas=js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                //Datas newDatas = js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                if (datas?.Count > 0)
                {
                    Posts posts = datas?.Posts[0];
                    this.index_title.Content = posts?.Title.ToString().Trim();
                    this.index_content.Text = HttpHelper.FiltHtmlCode(posts?.Excerpt.ToString().Trim());
                }
            }
        }
    }
}
