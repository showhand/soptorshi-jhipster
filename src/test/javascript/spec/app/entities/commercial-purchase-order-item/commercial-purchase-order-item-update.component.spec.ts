/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderItemUpdateComponent } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item-update.component';
import { CommercialPurchaseOrderItemService } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item.service';
import { CommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrderItem Management Update Component', () => {
        let comp: CommercialPurchaseOrderItemUpdateComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderItemUpdateComponent>;
        let service: CommercialPurchaseOrderItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderItemUpdateComponent]
            })
                .overrideTemplate(CommercialPurchaseOrderItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialPurchaseOrderItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialPurchaseOrderItemService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPurchaseOrderItem(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPurchaseOrderItem = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialPurchaseOrderItem();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialPurchaseOrderItem = entity;
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
