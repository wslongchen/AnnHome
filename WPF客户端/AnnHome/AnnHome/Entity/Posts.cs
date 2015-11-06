using System;
using System.Collections.Generic;

namespace AnnHome.Entity
{
    [Serializable]
    internal class Posts: List<Object>
    {
        private int id;
        private string type;
        private string slug;
        private string url;
        private string status;
        private string title;
        private string title_plain;
        private string content;
        private string excerpt;
        private string date;
        private string modified;
        private List<Categories> categories;
        private List<Tags> tags;
        private Author author;
        private List<Comments> comments;
        private List<Attachments> attachments;
        private int comment_count;
        private string comment_status;
        private string thumbnail;
        //private string custom_fields;
        private string thumbnail_size;
        private Images thumbnail_images;

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

        public string Type
        {
            get
            {
                return type;
            }

            set
            {
                type = value;
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

        public string Title_plain
        {
            get
            {
                return title_plain;
            }

            set
            {
                title_plain = value;
            }
        }

        public string Content
        {
            get
            {
                return content;
            }

            set
            {
                content = value;
            }
        }

        public string Excerpt
        {
            get
            {
                return excerpt;
            }

            set
            {
                excerpt = value;
            }
        }

        public string Date
        {
            get
            {
                return date;
            }

            set
            {
                date = value;
            }
        }

        public string Modified
        {
            get
            {
                return modified;
            }

            set
            {
                modified = value;
            }
        }

        public List<Categories> Categories
        {
            get
            {
                return categories;
            }

            set
            {
                categories = value;
            }
        }

        public List<Tags> Tags
        {
            get
            {
                return tags;
            }

            set
            {
                tags = value;
            }
        }

        public Author Author
        {
            get
            {
                return author;
            }

            set
            {
                author = value;
            }
        }

        public List<Comments> Comments
        {
            get
            {
                return comments;
            }

            set
            {
                comments = value;
            }
        }

        public List<Attachments> Attachments
        {
            get
            {
                return attachments;
            }

            set
            {
                attachments = value;
            }
        }

        public int Comment_count
        {
            get
            {
                return comment_count;
            }

            set
            {
                comment_count = value;
            }
        }

        public string Comment_status
        {
            get
            {
                return comment_status;
            }

            set
            {
                comment_status = value;
            }
        }

        public string Thumbnail
        {
            get
            {
                return thumbnail;
            }

            set
            {
                thumbnail = value;
            }
        }

        //public string Custom_fields
        //{
        //    get
        //    {
        //        return custom_fields;
        //    }

        //    set
        //    {
        //        custom_fields = value;
        //    }
        //}

        public string Thumbnail_size
        {
            get
            {
                return thumbnail_size;
            }

            set
            {
                thumbnail_size = value;
            }
        }

        public Images Thumbnail_images
        {
            get
            {
                return thumbnail_images;
            }

            set
            {
                thumbnail_images = value;
            }
        }
    }

    
}