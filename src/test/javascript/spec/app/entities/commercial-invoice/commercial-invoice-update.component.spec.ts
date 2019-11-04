/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialInvoiceUpdateComponent } from 'app/entities/commercial-invoice/commercial-invoice-update.component';
import { CommercialInvoiceService } from 'app/entities/commercial-invoice/commercial-invoice.service';
import { CommercialInvoice } from 'app/shared/model/commercial-invoice.model';

describe('Component Tests', () => {
    describe('CommercialInvoice Management Update Component', () => {
        let comp: CommercialInvoiceUpdateComponent;
        let fixture: ComponentFixture<CommercialInvoiceUpdateComponent>;
        let service: CommercialInvoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialInvoiceUpdateComponent]
            })
                .overrideTemplate(CommercialInvoiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialInvoiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialInvoiceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialInvoice(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialInvoice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialInvoice();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialInvoice = entity;
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
