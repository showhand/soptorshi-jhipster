/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderUpdateComponent } from 'app/entities/commercial-purchase-order/commercial-purchase-order-update.component';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order/commercial-purchase-order.service';
import { CommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrder Management Update Component', () => {
        let comp: CommercialPurchaseOrderUpdateComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderUpdateComponent>;
        let service: CommercialPurchaseOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderUpdateComponent]
            })
                .overrideTemplate(CommercialPurchaseOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPurchaseOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPurchaseOrderService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPurchaseOrder(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPurchaseOrder = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPurchaseOrder();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPurchaseOrder = entity;
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
