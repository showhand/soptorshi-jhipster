/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialProformaInvoiceUpdateComponent } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice-update.component';
import { CommercialProformaInvoiceService } from 'app/entities/commercial-proforma-invoice/commercial-proforma-invoice.service';
import { CommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';

describe('Component Tests', () => {
    describe('CommercialProformaInvoice Management Update Component', () => {
        let comp: CommercialProformaInvoiceUpdateComponent;
        let fixture: ComponentFixture<CommercialProformaInvoiceUpdateComponent>;
        let service: CommercialProformaInvoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialProformaInvoiceUpdateComponent]
            })
                .overrideTemplate(CommercialProformaInvoiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialProformaInvoiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialProformaInvoiceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialProformaInvoice(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialProformaInvoice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialProformaInvoice();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialProformaInvoice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
