package idv.eason.image;

public class tt {
    
    
    double A = B = C = D = E = F = 1.0; // ???
    //A = B = C = D = E = F = 1.0; // 先初始化
//     (X, Y)在第一張，(x, y)在第二張
//     X = Ax + By + C
//     Y = Dx + Ey + F
    /*
    X1 = Ax1 + By1 + 1C = X1 | x1 y1 1 |
    X2 = Ax2 + By2 + 1C = X2 | x2 y2 1 |
    X3 = Ax3 + By3 + 1C = X3 | x3 y3 1 |
    Y1 = Dx1 + Ey1 + 1F = Y1
    Y2 = Dx2 + Ey2 + 1F = Y2
    Y3 = Dx3 + Ey3 + 1F = Y3
    */
//  計算???
    calsixpara(rndvar.tria[0], rndvar.tria[1], &A, &B, &C, &D, &E, &F);
//     計算第二張圖頂點新座標
    RectStruct oldrect;
    oldrect = rndvar.rect[1]; // 記?舊座標
    rndvar.rect[1].v[0].x = A * oldrect.v[0].x + B * oldrect.v[0].y + C;
    rndvar.rect[1].v[0].y = D * oldrect.v[0].x + E * oldrect.v[0].y + F;
    rndvar.rect[1].v[1].x = A * oldrect.v[1].x + B * oldrect.v[1].y + C;
    rndvar.rect[1].v[1].y = D * oldrect.v[1].x + E * oldrect.v[1].y + F;
    rndvar.rect[1].v[2].x = A * oldrect.v[2].x + B * oldrect.v[2].y + C;
    rndvar.rect[1].v[2].y = D * oldrect.v[2].x + E * oldrect.v[2].y + F;
    rndvar.rect[1].v[3].x = A * oldrect.v[3].x + B * oldrect.v[3].y + C;
    rndvar.rect[1].v[3].y = D * oldrect.v[3].x + E * oldrect.v[3].y + F;
//     將?圖共八個頂點的最小(x, y)歸0
    double minx, miny, maxx, maxy;
    int i;
    minx = maxx = rndvar.rect[0].v[0].x;
    miny = maxy = rndvar.rect[0].v[0].y;
    for (i=1; i < 4; i++) // 第一張rect[0]
    {
    if ( rndvar.rect[0].v[i].x < minx )
    minx = rndvar.rect[0].v[i].x;
    if ( rndvar.rect[0].v[i].x > maxx )
    maxx = rndvar.rect[0].v[i].x;
    if ( rndvar.rect[0].v[i].y < miny )
    miny = rndvar.rect[0].v[i].y;
    if ( rndvar.rect[0].v[i].y > maxy )
    maxy = rndvar.rect[0].v[i].y;
    } // end of for 第一張
    for (i=0; i < 4; i++) // 第二張
    {
        if ( rndvar.rect[1].v[i].x < minx )
        minx = rndvar.rect[1].v[i].x;
        if ( rndvar.rect[1].v[i].x > maxx )
        maxx = rndvar.rect[1].v[i].x;
        if ( rndvar.rect[1].v[i].y < miny )
        miny = rndvar.rect[1].v[i].y;
        if ( rndvar.rect[1].v[i].y > maxy )
        maxy = rndvar.rect[1].v[i].y;
        } // end of for 第二張
        double dx, dy; // 記?(minx, miny)與(0, 0)的差值
        dx = 0.0 - minx;
        dy = 0.0 - miny;
//         將最小的歸0
        int p;
        for (p=0; p < 2; p++) // ?張圖
        for (i=0; i < 4; i++) // 各四個頂點
        {
        rndvar.rect[p].v[i].x += dx;
        rndvar.rect[p].v[i].y += dy;
        } // end of i, p 最小歸0
        maxx += dx; // 同時修正最大座標
        maxy += dy;
//         開始配置大圖及貼圖
        int width = (int)round(maxx) + 1;
        int height = (int)round(maxy) + 1;
        int blen = (width * 3 + 3) & ~3;
        GLubyte* tbmp = new GLubyte[blen * height]; // openGL 指?，配置大圖
        clearbmp(tbmp, width, height, 255); // 將圖清為指定色階(白色)
//         直接貼上第一張圖
        pasteabmp(tbmp, width, height, rndvar.rect[0].v[0].x, rndvar.rect[0].v[0].y,
        rndvar.bmp1.getbits(), rndvar.bmp1.getwidth(), rndvar.bmp1.getheight());
        rndvar.resbmp.setbmp(tbmp, width, height);
//         變形貼上第二張圖
//         先找出第二張圖範圍
        minx = maxx = rndvar.rect[1].v[0].x;
        miny = maxy = rndvar.rect[1].v[0].y;
        for (i=1; i < 4; i++) // 找出最大、最小的座標範圍
        {
        if (rndvar.rect[1].v[i].x < minx)
        minx = rndvar.rect[1].v[i].x;
        if (rndvar.rect[1].v[i].x > maxx)
        maxx = rndvar.rect[1].v[i].x;
        if (rndvar.rect[1].v[i].y < miny)
        miny = rndvar.rect[1].v[i].y;
        if (rndvar.rect[1].v[i].y > maxy)
        maxy = rndvar.rect[1].v[i].y;
        } // end of for i
        double x, y;
        Pix24Class apixel;
        Pix24Class bpixel;
        double alfa, beta, ax, ay;
//         逐點計算，逆向變形
        for ( y= floor(miny); y <= floor(maxy); y += 1.0)
        for (x = floor(minx); x <= floor(maxx); x += 1.0)
        {
//         計算barycentric
        if (barycentric(rndvar.rect[1].v[0], rndvar.rect[1].v[1],
        rndvar.rect[1].v[2], x, y, &alfa, &beta) )
        {
//         在右下邊內
//         計算原圖中的位置
        calbary(oldrect.v[0], oldrect.v[1], oldrect.v[2],
        alfa, beta, &ax, &ay);
//         以內插修正方式取得pixel
        apixel = bilinear(rndvar.bmp2, ax, ay);
        bpixel = rndvar.resbmp.getpixel(x, y);
        if (bpixel.getB() >= 200 && bpixel.getG() >= 200
        && bpixel.getR() >= 200)
        {
        rndvar.resbmp.setpixel(x, y, apixel);
        }
        else
        {
        apixel = apixel / 2 + bpixel / 2;
        rndvar.resbmp.setpixel(x, y, apixel); // 設定pixel 至resbmp 中
        }
        } // end of if 在右下邊
        else if (barycentric(rndvar.rect[1].v[0], rndvar.rect[1].v[2],
        rndvar.rect[1].v[3], x, y, &alfa, &beta) )
        {
//         在左上邊
        calbary(oldrect.v[0], oldrect.v[2], oldrect.v[3],
        alfa, beta, &ax, &ay);
        apixel = bilinear(rndvar.bmp2, ax, ay);
        bpixel = rndvar.resbmp.getpixel(x, y);
        if (bpixel.getB() >= 200 && bpixel.getG() >= 200
        && bpixel.getR() >= 200)
        {
        rndvar.resbmp.setpixel(x, y, apixel);
        }
        else
        {
        apixel = apixel / 2 + bpixel / 2;
        rndvar.resbmp.setpixel(x, y, apixel); // 設定pixel 至resbmp 中
        }
        } // end of else 在左上
        } // end of for x, y 變形貼圖
//         已完成大圖
//         計算???，及其他所需副程式
//         計算2x2 ??式
//         |a c|
//         |b d|
        double det2x2(double a, double b, double c, double d) // determinate 2x2
        {
        return (a * d - b * c);
        } // end of det2x2
//         計算3x3 ??式
//         | a1 b1 c1 |
//      | a2 b2 c2 |
//      | a3 b3 c3 |
     double det3x3(double a1, double a2, double a3, double b1, double b2, double b3,
     double c1, double c2, double c3) // determinate 3x3
     {
     double ans;
     ans = a1 * det2x2(b2, b3, c2, c3) - b1 * det2x2(a2, a3, c2, c3) +
     c1 * det2x2(a2, a3, b2, b3);
     return ans;
     } // end of det3x3
//      解三元一次??方程式
//      a1x + b1y + c1z = d1
//      a2x + b2y + c2z = d2
//      a3x + b3y + c3z = d3
//      有解則TRUE，否則為FALSE
     BOOL tripleone(double a1, double a2, double a3, double b1, double b2, double b3,
     double c1, double c2, double c3, double d1, double d2, double d3,
     double* x, double* y, double* z) // 解三元一次??方程式
     {
     double dbase;
     dbase = det3x3(a1, a2, a3, b1, b2, b3, c1, c2, c3);
     if ( fabs(dbase) < TOL ) return FALSE; // 相當於0
     *x = det3x3(d1, d2, d3, b1, b2, b3, c1, c2, c3) / dbase;
     *y = det3x3(a1, a2, a3, d1, d2, d3, c1, c2, c3) / dbase;
     *z = det3x3(a1, a2, a3, b1, b2, b3, d1, d2, d3) / dbase;
     return TRUE;
     } // end of tripleone
     int round(double r) // 四捨五入
     {
     double x;
     x = floor(r);
     if (r - x > 0.5) return (int)(x+1);
     return (int)x;
     } // end of round
//   barycentric subroutin
     BOOL barycentric(VertexStruct& centric, VertexStruct& V1, VertexStruct& V2,
     double inx, double iny, double* alfa, double* beta)
     {
     double ar1x, ar1y, ar2x, ar2y; // arrows
     ar1x = V1.x - centric.x;
     ar1y = V1.y - centric.y;
     ar2x = V2.x - centric.x;
     ar2y = V2.y - centric.y;
     inx -= centric.x;
     iny -= centric.y;
     double solbase = ar1x * ar2y - ar1y * ar2x;
     double solalfa = inx * ar2y - iny * ar2x;
     double solbeta = ar1x * iny - ar1y * inx;
     *alfa = solalfa / solbase;
     *beta = solbeta / solbase;
     if (*alfa > 0.0-TOL && *beta > 0.0-TOL && *alfa + *beta <= 1.0+TOL)
     return TRUE;
     return FALSE;
     } // end of barycentric
//      由alfa, beta 換算成ax, ay
//      傳入三頂點，alfa, beta，由*ax, *ay 傳回座標
     void calbary(VertexStruct& v0, VertexStruct& v1, VertexStruct& v2,
     double alfa, double beta, double* ax, double* ay)
     {
     double x1 = v1.x - v0.x; // 向?0->1
     double y1 = v1.y - v0.y;
     double x2 = v2.x - v0.x; // 向?0->2
     double y2 = v2.y - v0.y;
     *ax = alfa * x1 + beta * x2 + v0.x;
     *ay = alfa * y1 + beta * y2 + v0.y;
     } // end of calbary
//      bilinear
//   傳入??：圖、在該圖中的座標； 傳回：一 pixel
     Pix24Class bilinear(Bmp24FClass& bmp, double x, double y)
     {
     int ix = (int)floor(x);
     int iy = (int)floor(y);
     double t = x - (double)ix;
     double u = y - (double)iy;
     Pix24Class respixel, Apixel, Bpixel, Cpixel, Dpixel;
     Cpixel = bmp.getpixel(ix, iy); // 左下
     Dpixel = bmp.getpixel(ix+1, iy); // 右下
     Bpixel = bmp.getpixel(ix, iy+1); // 左上
     Apixel = bmp.getpixel(ix+1, iy+1); // 右上
     respixel = (1.0 - t) * (1.0 - u) * Cpixel +
     t * (1.0 - u) * Dpixel +
     (1.0 - t) * u * Bpixel +
     t * u * Apixel;
     return respixel;
     } // end of bilinear
//     貼小圖在大圖中，貼圖時判斷是否過邊，過邊的部分?貼
//     傳入??：原大圖資?，大圖寬、高，貼圖的起始位置(sx,sy)，
//      及小圖資?，小圖寬、高
     void pasteabmp(GLubyte* bigbmp, int bigw, int bigh, int sx, int sy,
     GLubyte* partbmp, int width , int height)
     {
     int clipright,cliptop; //記?過邊的???，過邊的部分?貼上
     int tempy,tempx;
     int length,partlen;
     int curx,partcurx;
     int w,h;
     w=width;
     h=height;
     if (sx + w > bigw) // 超過右邊界
     {
     clipright = sx + w - bigw;
     w -= clipright; //小圖寬減掉過邊的部分
     }
     if (sy + h > bigh) //超過上邊界
     {
     cliptop = sy + h - bigh;
     h -= cliptop; //小圖高減掉過邊的部分
     }
     length = (bigw * 3 + 3) & ~3;
     partlen = (width * 3 + 3) & ~3;
     for (tempy=0; tempy < h; tempy++)
     {
     curx = (tempy + sy) * length + sx * 3;
     partcurx = tempy * partlen;
     for (tempx=0; tempx < w; tempx++)
     {
     bigbmp[curx] = partbmp[partcurx];
     bigbmp[curx+1] = partbmp[partcurx+1];
     bigbmp[curx+2] = partbmp[partcurx+2];
     curx += 3;
     partcurx += 3;
     } // end of for pixel
     } // end of for row
     } // end of function pasteabmp
//      計算???
//      傳入對映的?組三點座標，由*A,*B,*C,*D,*E,*F ???傳回
     BOOL calsixpara(TriaStruct& tria0, TriaStruct& tria1, double* A, double* B,
     double* C, double* D, double* E, double* F)
     {
     double dbase = det3x3(tria1.v[0].x, tria1.v[1].x, tria1.v[2].x,
     tria1.v[0].y, tria1.v[1].y, tria1.v[2].y,
     1.0, 1.0, 1.0);
     if (dbase < TOL)
     {
     *C = tria0.v[0].x - tria1.v[0].x;
     *F = tria0.v[0].y - tria1.v[0].y;
     return FALSE; // 相當於0
     }
     *A = det3x3(tria0.v[0].x, tria0.v[1].x, tria0.v[2].x,
     tria1.v[0].y, tria1.v[1].y, tria1.v[2].y,
     1.0, 1.0, 1.0) / dbase;
     *B = det3x3(tria1.v[0].x, tria1.v[1].x, tria1.v[2].x,
     tria0.v[0].x, tria0.v[1].x, tria0.v[2].x,
     1.0, 1.0, 1.0) / dbase;
     *C = det3x3(tria1.v[0].x, tria1.v[1].x, tria1.v[2].x,
     tria1.v[0].y, tria1.v[1].y, tria1.v[2].y,
     tria0.v[0].x, tria0.v[1].x, tria0.v[2].x) / dbase;
     *D = det3x3(tria0.v[0].y, tria0.v[1].y, tria0.v[2].y,
     tria1.v[0].y, tria1.v[1].y, tria1.v[2].y,
     1.0, 1.0, 1.0) / dbase;
     *E = det3x3(tria1.v[0].x, tria1.v[1].x, tria1.v[2].x,
     tria0.v[0].y, tria0.v[1].y, tria0.v[2].y,
     1.0, 1.0, 1.0) / dbase;
     *F = det3x3(tria1.v[0].x, tria1.v[1].x, tria1.v[2].x,
     tria1.v[0].y, tria1.v[1].y, tria1.v[2].y,
     tria0.v[0].y, tria0.v[1].y, tria0.v[2].y) / dbase;
     return TRUE;
     } // end of calsixpara
//      將圖清為指定色階
     void clearbmp(GLubyte* tbmp, int width, int height, GLubyte gray)
     {
     int blen = (width * 3 + 3) & ~3;
     int y, x, curx;
     for (y=0; y < height; y++)
     {
     curx = y * blen;
     for (x=0; x < width; x++)
     {
     tbmp[curx] = tbmp[curx+1] = tbmp[curx+2] = gray;
     curx += 3;
     } // end of for x
     } // end of for y
     } // end of clearbmp
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
