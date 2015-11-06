using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AnnHome.Entity
{
    internal class Datas
    {
        private string status;
        private int count;
        private int count_total;
        private int pages;
        private List<Posts> posts;

        public string Status
        {
            get
            {
                return status;
            }

            set
            {
                status = value;
            }
        }

        public int Count
        {
            get
            {
                return count;
            }

            set
            {
                count = value;
            }
        }

        public int Count_total
        {
            get
            {
                return count_total;
            }

            set
            {
                count_total = value;
            }
        }

        public int Pages
        {
            get
            {
                return pages;
            }

            set
            {
                pages = value;
            }
        }

        public List<Posts> Posts
        {
            get
            {
                return posts;
            }

            set
            {
                posts = value;
            }
        }
    }
}
