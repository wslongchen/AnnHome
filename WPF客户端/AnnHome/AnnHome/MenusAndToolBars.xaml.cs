using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
    /// Interaction logic for MenusAndToolBars.xaml
    /// </summary>
    public partial class MenusAndToolBars : UserControl
    {

        private TextRange select_range;

        public MenusAndToolBars()
        {
            InitializeComponent();
            DataContext = new PaletteSelectorViewModel();
            InitializeFontFamilyList();
        }

        void init()
        {
             select_range = this.rt_file.Selection;
            
        }

        private void InitializeFontFamilyList()
        {
            foreach (FontFamily font in Fonts.SystemFontFamilies)
            {
                ComboBoxItem a = new ComboBoxItem();
                a.FontFamily = font;
                a.Content = font.Source;
                cmb.Items.Add(a);

            }
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            string filepath = "";
            OpenFileDialog openfilejpg = new OpenFileDialog();
            openfilejpg.Filter = "jpg图片(*.jpg)|*.jpg|gif图片(*.gif)|*.gif";
            openfilejpg.FilterIndex = 0;
            openfilejpg.RestoreDirectory = true;
            openfilejpg.Multiselect = false;
            if (openfilejpg.ShowDialog() == true)
            {
                filepath = openfilejpg.FileName;
                Image img = new Image();
                BitmapImage bImg = new BitmapImage();
                img.IsEnabled = true;
                bImg.BeginInit();
                bImg.UriSource = new Uri(filepath, UriKind.Relative);
                bImg.EndInit();
                img.Source = bImg;
                FontFamily font = new FontFamily();
                
                //MessageBox.Show(bImg.Width.ToString() + "," + bImg.Height.ToString());
                /* 调整图片大小
                if (bImg.Height > 100 || bImg.Width > 100)
                {
                    img.Height = bImg.Height * 0.2;
                    img.Width = bImg.Width * 0.2;
                }*/
                img.Stretch = Stretch.Uniform;  //图片缩放模式
                new InlineUIContainer(img, rt_file.Selection.Start); //插入图片到选定位置

            }
        }

        private void font_size_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (this.font_size.SelectedIndex != -1)
            {
                ComboBoxItem item = font_size.SelectedValue as ComboBoxItem;
                if (item.Content != null && rt_file.Selection.Text!="")
                {
                    rt_file.Selection.ApplyPropertyValue(TextElement.FontSizeProperty, item.Content);
                }
                //this.rt_file.Selection.ApplyPropertyValue(TextElement.FontSizeProperty,fontSize);

            }
        }

        private void cmb_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (this.cmb.SelectedIndex != -1)
            {
                ComboBoxItem item = cmb.SelectedValue as ComboBoxItem;
                if (item.Content != null && rt_file.Selection.Text != "")
                {
                    rt_file.Selection.ApplyPropertyValue(TextElement.FontFamilyProperty, item.Content);
                }

            }
        }
    }
}
