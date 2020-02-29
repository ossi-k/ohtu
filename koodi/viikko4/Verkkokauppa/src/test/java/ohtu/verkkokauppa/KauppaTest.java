package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
//import ohtu.verkkokauppa.Pankki;

public class KauppaTest {

    Pankki pankki;
    Varasto varasto;
    Viitegeneraattori viite;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);

        viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        varasto = mock(Varasto.class);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostetaanKaksiEriTuotetta() {

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);

        Kauppa k = new Kauppa(varasto, pankki, viite);

        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "omenamehu", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kalja", 10));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(15));

    }

    @Test
    public void ostetaanKaksiSamaaTuotetta() {
        when(varasto.saldo(1)).thenReturn(10);
        Kauppa k = new Kauppa(varasto, pankki, viite);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "kananmuna", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(4));

    }

    @Test
    public void ostetaanLoppunuttaTuotetta() {
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        
        Kauppa k = new Kauppa(varasto, pankki, viite);

        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "kananmuna", 2));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "puhelin", 20));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(2));

    }
}
