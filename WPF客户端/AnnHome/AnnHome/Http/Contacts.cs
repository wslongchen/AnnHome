using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AnnHome.Http
{
    class Contacts
    {
        private static string CMD_ARTICLE_DATE = "get_date_posts&date=";
        private static string CMD_RECENT_ARTICLE = "get_recent_posts";

        public static string getArticlesByDate(string date)
        {
            return makeCommand(CMD_ARTICLE_DATE) + date;
        }

        public static string getAllArticles()
        {
            return makeCommand(CMD_RECENT_ARTICLE);
        }

        private static string makeCommand(string command)
        {
            return String.Format("http://www.mrpann.com" + "/?json={0}",command);
        }
    }
}
