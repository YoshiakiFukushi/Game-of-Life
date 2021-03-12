package gol;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;//値の入力を求めたり、情報を通知したりするためのポップアップできるようにします。

public class a_Frame extends JFrame
{
	private Screen screen;//下にあるScreenクラスを生成
	private b_Simulation simulation;//b_simulationからクラスを生成
	private float PROGRESSTIME;
	private float PAUSETIME = 0.1f;
	public a_Frame()
	{//インスタンス
		String input = JOptionPane.showInputDialog(this,"セルの大きさ");//ユーザーに入力を求めるメッセージを表示します。
		try
		{
			c_Cell.size = Integer.parseInt(input);//入力された数字をそのままセルの大きさとして代入します。
		}catch(Exception e)
		{
			System.exit(0);//現在実行しているJava仮想マシンを終了します。//解析不可の場合はそのまま終了
		}

		input = JOptionPane.showInputDialog(this,"静止時間");//次はフレーム速度の入力を求め
		try
		{
			PAUSETIME = Float.parseFloat(input);//今度一時停止停止時間に代入。（これはクールタイム短いほど速く展開される）
		}catch(Exception e)
		{
			System.exit(0);//現在実行しているJava仮想マシンを終了します。//解析不可の場合はそのまま終了
		}
		setExtendedState(MAXIMIZED_BOTH);
	}
	public void createScreen()
	{//インスタンスでは無理だったので代わりに生成したscreenをサイズ調整するvoid型
		simulation = new b_Simulation();//加えてb_Simulatonクラスの型も宣言する
		//マウスやキーボード認識するクラスをb_Simulatonクラスのsimulationから持ってくる
		addKeyListener(simulation);
		addMouseListener(simulation);
		addMouseMotionListener(simulation);
		
		screen = new Screen();//スクリーンのサイズはMainクラスのwidth、heightから持ってきている
		screen.setBounds(0,0,Main.width,Main.height);//新しい境界の矩形に適合するように、サイズ変更し、新しい位置はr.xとr.y、新しいサイズはr.widthとr.heightによって指定されます。
		add(screen);//設定したscreenを追加で表示させる
	}
	public void update(float frameTime)
	{
		PROGRESSTIME += frameTime;//経過時間を足していく
		if(PROGRESSTIME > PAUSETIME)//一時停止設定時間より多くなると
		{
			PROGRESSTIME = 0;//0に戻され　
			simulation.update();//そして、次の状態にupdateされる
		}
	}
	public void repaint()
	{//次の状態にupdateした状態を再度塗り変えるような関数
		screen.repaint();//このコンポーネントscreenをふたたびペイントします。
	}
	private class Screen extends JLabel
	{
		@Override
		public void paintComponent(Graphics g)
		{
		super.paintComponent(g);

		simulation.draw(g);//b_Simulation内のdraw関数を実行（そのdraw関数は元々c_Cellクラス内の関数）
		}
	}
}
