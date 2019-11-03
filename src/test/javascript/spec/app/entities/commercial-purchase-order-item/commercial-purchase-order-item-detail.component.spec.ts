/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderItemDetailComponent } from 'app/entities/commercial-purchase-order-item/commercial-purchase-order-item-detail.component';
import { CommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrderItem Management Detail Component', () => {
        let comp: CommercialPurchaseOrderItemDetailComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderItemDetailComponent>;
        const route = ({ data: of({ commercialPurchaseOrderItem: new CommercialPurchaseOrderItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPurchaseOrderItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPurchaseOrderItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPurchaseOrderItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
