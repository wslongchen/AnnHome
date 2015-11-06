using System;
using System.Collections.Generic;

namespace AnnHome.Entity
{
    [Serializable]
    internal class Attachments:List<Object>
    {
        private int id;
        private string url;
        private string slug;
        private string title;
        private string description;
        private string caption;
        private int parent;
        private string mime_type;
        private Images images;

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

        public string Url
        {
            get
            {
                return url;
            }

            set
            {
                url = value;
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

        public string Caption
        {
            get
            {
                return caption;
            }

            set
            {
                caption = value;
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

        public string Mime_type
        {
            get
            {
                return mime_type;
            }

            set
            {
                mime_type = value;
            }
        }

        public Images Images
        {
            get
            {
                return images;
            }

            set
            {
                images = value;
            }
        }
    }
}