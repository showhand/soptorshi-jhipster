/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPurchaseOrderDetailComponent } from 'app/entities/commercial-purchase-order/commercial-purchase-order-detail.component';
import { CommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

describe('Component Tests', () => {
    describe('CommercialPurchaseOrder Management Detail Component', () => {
        let comp: CommercialPurchaseOrderDetailComponent;
        let fixture: ComponentFixture<CommercialPurchaseOrderDetailComponent>;
        const route = ({ data: of({ commercialPurchaseOrder: new CommercialPurchaseOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPurchaseOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPurchaseOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPurchaseOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPurchaseOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
