package gol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class b_Simulation implements KeyListener,MouseListener,MouseMotionListener
{
	private c_Cell[][]cells;//プライベートクラスとしてx*y個分を管理できる二重配列を用意する
	private Random r;//java.util.RandomからRandom型を用意
	private int w = Main.width/c_Cell.size;//Mainクラスの横幅/c_Cellクラスのサイズの値で割ると横に並ぶCellsの数がわかる！
	private int h = Main.height/c_Cell.size;//縦も同じく(int型なので小数点以下切り捨て)
	private int generation;
	private int button;
	private boolean go;
	public b_Simulation()
	{//インスタンス
		r = new Random();//インスタンス内にRandomをセットする

		cells = new c_Cell[w][h];//求めた縦・横に並ぶセルの数分の配列をそれぞれ宣言する！
		for(int x = 0;x<w;x++)
		{
			for(int y = 0;y<h;y++)
			{
				cells[x][y] = new c_Cell(x,y);//配列のほかにも(x,y)座標それぞれにおいて宣言
				cells[x][y].setAlive(false);//生死判定にかかわるセッターにRandomクラスを使い、無作為に初期状態を決める
			}
		}
	}
	public void update()
	{//現在状態(初期状態)から次の生死状態を判定する関数
		if(go)
		{
			generation++;
			for(int x = 0;x < w;x++) {
				for (int y = 0; y < h; y++) {
					int mx = x - 1;
					if(mx < 0) mx = w - 1;
					int my = y - 1;
					if(my < 0) my = h - 1;
					int gx = (x + 1) % w;//一番上と一番下と上辺が繋がっている状態
					int gy = (y + 1) % h;//右端と左端が繋がっている状態
					//b_Simulationクラスの関数から現在状態の縦・横・斜めの生死状態を把握するゲッターから次の状態の生死状態を決める
					int neighborCounter = 0;//生死判定に必要なカウント
					if(cells[mx] [my].getAlive()) neighborCounter++;
					if(cells[mx] [y].getAlive()) neighborCounter++;
					if(cells[mx] [gy].getAlive()) neighborCounter++;
					if(cells[x] [my].getAlive()) neighborCounter++;
					if(cells[x] [gy].getAlive()) neighborCounter++;
					if(cells[gx] [my].getAlive()) neighborCounter++;
					if(cells[gx] [y].getAlive()) neighborCounter++;
					if(cells[gx] [gy].getAlive()) neighborCounter++;
					//カウント数によって、次の状態の一つ一つのセルの生死判定をする
					if(neighborCounter < 2 || neighborCounter > 4) cells[x] [y].setNextStage(false);//死…合計1以下もしくは5以上
					else if(neighborCounter == 2) cells[x] [y].setNextStage(cells[x] [y].getAlive());//維持…合計2の時
					else cells[x] [y].setNextStage(true);//生…それ以外(合計は3,4の時)
				}
			}
			for(int x = 0;x < w;x++) {
				for (int y = 0; y < h; y++) {
					cells[x] [y].NextStage();//ここで「次（みらい）」の状態を「現在（いま）」の状態に上書きする。
				}
			}
		}
	}
	public void draw(Graphics g)
	{
		for(int x = 0;x<w;x++)
		{
			for(int y = 0;y<h;y++)
			{
				cells[x][y].draw(g);//c_Cell内のdraw関数（クラス）が実行させれる
			}
		}
		g.setColor(Color.BLUE);
		g.setFont(new Font("SansSerif",Font.BOLD,25));//フォント指定
		g.drawString(""+generation,10,10+g.getFont().getSize());//ループ回（世代交代）数を表すgenerationの値を表示
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_R) {//Rキーを押すたびにランダムにセルを生成・消去を行う
			for(int x = 0;x<w;x++)
			{
				for(int y = 0;y<h;y++)
				{
					cells[x][y].setAlive(r.nextBoolean());//生死判定にかかわるセッターにRandomクラスを使い、無作為に初期状態を決める
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {//スペースを押すたびに一時停止と再生が切り替わる
			if(go)
				go = false;
			else go = true;
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!go)
		{
			int mx = e.getX()/c_Cell.size;
			int my = e.getY()/c_Cell.size;
			if(button == 1)cells[mx][my].setAlive(true);//buttonが1の（右クリックしてドラッグしている）時
			else cells[mx][my].setAlive(false);//buttonが-1の（左クリックしてドラッグしている）時
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!go)
		{
			int mx = e.getX()/c_Cell.size;
			int my = e.getY()/c_Cell.size;
			if(button == 1)cells[mx][my].setAlive(true);//buttonが1の（右クリックしてドラッグしている）時
			else if(button != -1) cells[mx][my].setAlive(false);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		button = e.getButton();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		button = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
}

