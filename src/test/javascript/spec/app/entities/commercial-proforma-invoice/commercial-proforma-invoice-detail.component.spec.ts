/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProformaInvoiceDetailComponent } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice-detail.component';
import { CommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';

describe('Component Tests', () => {
    describe('CommercialProformaInvoice Management Detail Component', () => {
        let comp: CommercialProformaInvoiceDetailComponent;
        let fixture: ComponentFixture<CommercialProformaInvoiceDetailComponent>;
        const route = ({ data: of({ commercialProformaInvoice: new CommercialProformaInvoice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProformaInvoiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialProformaInvoiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialProformaInvoiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialProformaInvoice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
