/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderVoucherRelationUpdateComponent } from 'app/entities/purchase-order-voucher-relation/purchase-order-voucher-relation-update.component';
import { PurchaseOrderVoucherRelationService } from 'app/entities/purchase-order-voucher-relation/purchase-order-voucher-relation.service';
import { PurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';

describe('Component Tests', () => {
    describe('PurchaseOrderVoucherRelation Management Update Component', () => {
        let comp: PurchaseOrderVoucherRelationUpdateComponent;
        let fixture: ComponentFixture<PurchaseOrderVoucherRelationUpdateComponent>;
        let service: PurchaseOrderVoucherRelationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderVoucherRelationUpdateComponent]
            })
                .overrideTemplate(PurchaseOrderVoucherRelationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseOrderVoucherRelationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderVoucherRelationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseOrderVoucherRelation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseOrderVoucherRelation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseOrderVoucherRelation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseOrderVoucherRelation = entity;
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
