namespace AnnHome.Entity
{
    internal class Images
    {
        private Img full;
        private Img thumbnail;
        private Img medium;
        private Img large;

        public Img Full
        {
            get
            {
                return full;
            }

            set
            {
                full = value;
            }
        }

        public Img Thumbnail
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

        public Img Medium
        {
            get
            {
                return medium;
            }

            set
            {
                medium = value;
            }
        }

        public Img Large
        {
            get
            {
                return large;
            }

            set
            {
                large = value;
            }
        }
    }
}