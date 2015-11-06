using AnnHome.Entity;
using AnnHome.Http;
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
            //init();
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
        private void init()
        {
            int code = 0;
            string result = HttpHelper.RequestUrl(Contacts.getArticlesByDate("2015-11-01"), out code);
            if (code != -1)
            {
                JavaScriptSerializer js = new JavaScriptSerializer();
                Datas datas = js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                Posts posts = datas?.Posts[0];
                this.index_title.Content = posts?.Title.ToString().Trim();
                this.index_content.Text = HttpHelper.FiltHtmlCode(posts?.Excerpt.ToString().Trim());
            }

        }
    }
}
