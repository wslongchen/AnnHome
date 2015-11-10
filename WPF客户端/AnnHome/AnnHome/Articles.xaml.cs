using AnnHome.Entity;
using AnnHome.Http;
using AnnHome.Tools;
using System;
using System.Collections.Generic;
using System.Linq;
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
        private void init()
        {
            int code = 0;
            //显示最新文章
            string result = HttpHelper.RequestUrl(Contacts.getAllArticles(), out code);
            
            if (code != -1)
            {
                JavaScriptSerializer js = new JavaScriptSerializer();
                datas = js.Deserialize<Datas>(HttpHelper.formatJsonString(result));
                if (datas?.Count > 0)
                {
                    Posts posts = datas?.Posts[0];
                    a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
                    
                }
            }
        }

        private void btn_change_Click(object sender, RoutedEventArgs e)
        {
            if (pages < datas?.Posts.Count-1)
            {
                pages++;
                Posts posts = datas?.Posts[pages];
                a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
            }
            else
            {
                pages = 0;
                Posts posts = datas?.Posts[pages];
                a_content.NavigateToString(ToolHelper.ConvertExtendedASCII(posts?.Content));
            }
        }
    }
}
