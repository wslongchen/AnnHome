namespace AnnHome.Entity
{
    internal class Img
    {
        private string url;
        private int width;
        private int height;

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

        public int Width
        {
            get
            {
                return width;
            }

            set
            {
                width = value;
            }
        }

        public int Height
        {
            get
            {
                return height;
            }

            set
            {
                height = value;
            }
        }
    }
}