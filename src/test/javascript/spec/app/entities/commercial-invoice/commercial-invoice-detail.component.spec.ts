/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialInvoiceDetailComponent } from 'app/entities/commercial-invoice/commercial-invoice-detail.component';
import { CommercialInvoice } from 'app/shared/model/commercial-invoice.model';

describe('Component Tests', () => {
    describe('CommercialInvoice Management Detail Component', () => {
        let comp: CommercialInvoiceDetailComponent;
        let fixture: ComponentFixture<CommercialInvoiceDetailComponent>;
        const route = ({ data: of({ commercialInvoice: new CommercialInvoice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialInvoiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialInvoiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialInvoiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialInvoice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
