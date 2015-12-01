using System;
using System.Collections.Generic;
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
using System.Windows.Shapes;

namespace AnnHome
{
    /// <summary>
    /// Main.xaml 的交互逻辑
    /// </summary>
    public partial class Main : Window
    {
        public Main()
        {
            InitializeComponent();
        }

        private void TreeViewItem2_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            //myControl.Content = new Home();
            if (myControl.Content!=null)
                
                myControl.Content = new Home();
            //TreeViewItem a = (TreeViewItem)tree.SelectedItem;
            MessageBox.Show("22222");
        }
    }
}
