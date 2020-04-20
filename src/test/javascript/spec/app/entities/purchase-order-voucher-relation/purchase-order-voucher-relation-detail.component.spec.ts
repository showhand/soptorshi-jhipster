/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderVoucherRelationDetailComponent } from 'app/entities/purchase-order-voucher-relation/purchase-order-voucher-relation-detail.component';
import { PurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';

describe('Component Tests', () => {
    describe('PurchaseOrderVoucherRelation Management Detail Component', () => {
        let comp: PurchaseOrderVoucherRelationDetailComponent;
        let fixture: ComponentFixture<PurchaseOrderVoucherRelationDetailComponent>;
        const route = ({ data: of({ purchaseOrderVoucherRelation: new PurchaseOrderVoucherRelation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderVoucherRelationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseOrderVoucherRelationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseOrderVoucherRelationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseOrderVoucherRelation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
