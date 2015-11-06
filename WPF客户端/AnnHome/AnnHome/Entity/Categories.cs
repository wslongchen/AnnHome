using System;
using System.Collections.Generic;

namespace AnnHome.Entity
{
    [Serializable]
    internal class Categories:List<Object>
    {
        private int id;
        private string slug;
        private string title;
        private string description;
        private int parent;
        private int post_count;

        public int Id
        {
            get
            {
                return id;
            }

            set
            {
                id = value;
            }
        }

        public string Slug
        {
            get
            {
                return slug;
            }

            set
            {
                slug = value;
            }
        }

        public string Title
        {
            get
            {
                return title;
            }

            set
            {
                title = value;
            }
        }

        public string Description
        {
            get
            {
                return description;
            }

            set
            {
                description = value;
            }
        }

        public int Parent
        {
            get
            {
                return parent;
            }

            set
            {
                parent = value;
            }
        }

        public int Post_count
        {
            get
            {
                return post_count;
            }

            set
            {
                post_count = value;
            }
        }
    }
}