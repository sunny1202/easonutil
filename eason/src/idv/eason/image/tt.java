package idv.eason.image;

public class tt {
    
    
    double A = B = C = D = E = F = 1.0; // ???
    //A = B = C = D = E = F = 1.0; // ����l��
//     (X, Y)�b�Ĥ@�i�A(x, y)�b�ĤG�i
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
//  �p��???
    calsixpara(rndvar.tria[0], rndvar.tria[1], &A, &B, &C, &D, &E, &F);
//     �p��ĤG�i�ϳ��I�s�y��
    RectStruct oldrect;
    oldrect = rndvar.rect[1]; // �O?�®y��
    rndvar.rect[1].v[0].x = A * oldrect.v[0].x + B * oldrect.v[0].y + C;
    rndvar.rect[1].v[0].y = D * oldrect.v[0].x + E * oldrect.v[0].y + F;
    rndvar.rect[1].v[1].x = A * oldrect.v[1].x + B * oldrect.v[1].y + C;
    rndvar.rect[1].v[1].y = D * oldrect.v[1].x + E * oldrect.v[1].y + F;
    rndvar.rect[1].v[2].x = A * oldrect.v[2].x + B * oldrect.v[2].y + C;
    rndvar.rect[1].v[2].y = D * oldrect.v[2].x + E * oldrect.v[2].y + F;
    rndvar.rect[1].v[3].x = A * oldrect.v[3].x + B * oldrect.v[3].y + C;
    rndvar.rect[1].v[3].y = D * oldrect.v[3].x + E * oldrect.v[3].y + F;
//     �N?�Ϧ@�K�ӳ��I���̤p(x, y)�k0
    double minx, miny, maxx, maxy;
    int i;
    minx = maxx = rndvar.rect[0].v[0].x;
    miny = maxy = rndvar.rect[0].v[0].y;
    for (i=1; i < 4; i++) // �Ĥ@�irect[0]
    {
    if ( rndvar.rect[0].v[i].x < minx )
    minx = rndvar.rect[0].v[i].x;
    if ( rndvar.rect[0].v[i].x > maxx )
    maxx = rndvar.rect[0].v[i].x;
    if ( rndvar.rect[0].v[i].y < miny )
    miny = rndvar.rect[0].v[i].y;
    if ( rndvar.rect[0].v[i].y > maxy )
    maxy = rndvar.rect[0].v[i].y;
    } // end of for �Ĥ@�i
    for (i=0; i < 4; i++) // �ĤG�i
    {
        if ( rndvar.rect[1].v[i].x < minx )
        minx = rndvar.rect[1].v[i].x;
        if ( rndvar.rect[1].v[i].x > maxx )
        maxx = rndvar.rect[1].v[i].x;
        if ( rndvar.rect[1].v[i].y < miny )
        miny = rndvar.rect[1].v[i].y;
        if ( rndvar.rect[1].v[i].y > maxy )
        maxy = rndvar.rect[1].v[i].y;
        } // end of for �ĤG�i
        double dx, dy; // �O?(minx, miny)�P(0, 0)���t��
        dx = 0.0 - minx;
        dy = 0.0 - miny;
//         �N�̤p���k0
        int p;
        for (p=0; p < 2; p++) // ?�i��
        for (i=0; i < 4; i++) // �U�|�ӳ��I
        {
        rndvar.rect[p].v[i].x += dx;
        rndvar.rect[p].v[i].y += dy;
        } // end of i, p �̤p�k0
        maxx += dx; // �P�ɭץ��̤j�y��
        maxy += dy;
//         �}�l�t�m�j�ϤζK��
        int width = (int)round(maxx) + 1;
        int height = (int)round(maxy) + 1;
        int blen = (width * 3 + 3) & ~3;
        GLubyte* tbmp = new GLubyte[blen * height]; // openGL ��?�A�t�m�j��
        clearbmp(tbmp, width, height, 255); // �N�ϲM�����w�ⶥ(�զ�)
//         �����K�W�Ĥ@�i��
        pasteabmp(tbmp, width, height, rndvar.rect[0].v[0].x, rndvar.rect[0].v[0].y,
        rndvar.bmp1.getbits(), rndvar.bmp1.getwidth(), rndvar.bmp1.getheight());
        rndvar.resbmp.setbmp(tbmp, width, height);
//         �ܧζK�W�ĤG�i��
//         ����X�ĤG�i�Ͻd��
        minx = maxx = rndvar.rect[1].v[0].x;
        miny = maxy = rndvar.rect[1].v[0].y;
        for (i=1; i < 4; i++) // ��X�̤j�B�̤p���y�нd��
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
//         �v�I�p��A�f�V�ܧ�
        for ( y= floor(miny); y <= floor(maxy); y += 1.0)
        for (x = floor(minx); x <= floor(maxx); x += 1.0)
        {
//         �p��barycentric
        if (barycentric(rndvar.rect[1].v[0], rndvar.rect[1].v[1],
        rndvar.rect[1].v[2], x, y, &alfa, &beta) )
        {
//         �b�k�U�䤺
//         �p���Ϥ�����m
        calbary(oldrect.v[0], oldrect.v[1], oldrect.v[2],
        alfa, beta, &ax, &ay);
//         �H�����ץ��覡���opixel
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
        rndvar.resbmp.setpixel(x, y, apixel); // �]�wpixel ��resbmp ��
        }
        } // end of if �b�k�U��
        else if (barycentric(rndvar.rect[1].v[0], rndvar.rect[1].v[2],
        rndvar.rect[1].v[3], x, y, &alfa, &beta) )
        {
//         �b���W��
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
        rndvar.resbmp.setpixel(x, y, apixel); // �]�wpixel ��resbmp ��
        }
        } // end of else �b���W
        } // end of for x, y �ܧζK��
//         �w�����j��
//         �p��???�A�Ψ�L�һݰƵ{��
//         �p��2x2 ??��
//         |a c|
//         |b d|
        double det2x2(double a, double b, double c, double d) // determinate 2x2
        {
        return (a * d - b * c);
        } // end of det2x2
//         �p��3x3 ??��
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
//      �ѤT���@��??��{��
//      a1x + b1y + c1z = d1
//      a2x + b2y + c2z = d2
//      a3x + b3y + c3z = d3
//      ���ѫhTRUE�A�_�h��FALSE
     BOOL tripleone(double a1, double a2, double a3, double b1, double b2, double b3,
     double c1, double c2, double c3, double d1, double d2, double d3,
     double* x, double* y, double* z) // �ѤT���@��??��{��
     {
     double dbase;
     dbase = det3x3(a1, a2, a3, b1, b2, b3, c1, c2, c3);
     if ( fabs(dbase) < TOL ) return FALSE; // �۷��0
     *x = det3x3(d1, d2, d3, b1, b2, b3, c1, c2, c3) / dbase;
     *y = det3x3(a1, a2, a3, d1, d2, d3, c1, c2, c3) / dbase;
     *z = det3x3(a1, a2, a3, b1, b2, b3, d1, d2, d3) / dbase;
     return TRUE;
     } // end of tripleone
     int round(double r) // �|�ˤ��J
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
//      ��alfa, beta ���⦨ax, ay
//      �ǤJ�T���I�Aalfa, beta�A��*ax, *ay �Ǧ^�y��
     void calbary(VertexStruct& v0, VertexStruct& v1, VertexStruct& v2,
     double alfa, double beta, double* ax, double* ay)
     {
     double x1 = v1.x - v0.x; // �V?0->1
     double y1 = v1.y - v0.y;
     double x2 = v2.x - v0.x; // �V?0->2
     double y2 = v2.y - v0.y;
     *ax = alfa * x1 + beta * x2 + v0.x;
     *ay = alfa * y1 + beta * y2 + v0.y;
     } // end of calbary
//      bilinear
//   �ǤJ??�G�ϡB�b�ӹϤ����y�СF �Ǧ^�G�@ pixel
     Pix24Class bilinear(Bmp24FClass& bmp, double x, double y)
     {
     int ix = (int)floor(x);
     int iy = (int)floor(y);
     double t = x - (double)ix;
     double u = y - (double)iy;
     Pix24Class respixel, Apixel, Bpixel, Cpixel, Dpixel;
     Cpixel = bmp.getpixel(ix, iy); // ���U
     Dpixel = bmp.getpixel(ix+1, iy); // �k�U
     Bpixel = bmp.getpixel(ix, iy+1); // ���W
     Apixel = bmp.getpixel(ix+1, iy+1); // �k�W
     respixel = (1.0 - t) * (1.0 - u) * Cpixel +
     t * (1.0 - u) * Dpixel +
     (1.0 - t) * u * Bpixel +
     t * u * Apixel;
     return respixel;
     } // end of bilinear
//     �K�p�Ϧb�j�Ϥ��A�K�ϮɧP�_�O�_�L��A�L�䪺����?�K
//     �ǤJ??�G��j�ϸ�?�A�j�ϼe�B���A�K�Ϫ��_�l��m(sx,sy)�A
//      �Τp�ϸ�?�A�p�ϼe�B��
     void pasteabmp(GLubyte* bigbmp, int bigw, int bigh, int sx, int sy,
     GLubyte* partbmp, int width , int height)
     {
     int clipright,cliptop; //�O?�L�䪺???�A�L�䪺����?�K�W
     int tempy,tempx;
     int length,partlen;
     int curx,partcurx;
     int w,h;
     w=width;
     h=height;
     if (sx + w > bigw) // �W�L�k���
     {
     clipright = sx + w - bigw;
     w -= clipright; //�p�ϼe��L�䪺����
     }
     if (sy + h > bigh) //�W�L�W���
     {
     cliptop = sy + h - bigh;
     h -= cliptop; //�p�ϰ���L�䪺����
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
//      �p��???
//      �ǤJ��M��?�դT�I�y�СA��*A,*B,*C,*D,*E,*F ???�Ǧ^
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
     return FALSE; // �۷��0
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
//      �N�ϲM�����w�ⶥ
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
