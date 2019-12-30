/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderMessagesDetailComponent } from 'app/entities/purchase-order-messages/purchase-order-messages-detail.component';
import { PurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

describe('Component Tests', () => {
    describe('PurchaseOrderMessages Management Detail Component', () => {
        let comp: PurchaseOrderMessagesDetailComponent;
        let fixture: ComponentFixture<PurchaseOrderMessagesDetailComponent>;
        const route = ({ data: of({ purchaseOrderMessages: new PurchaseOrderMessages(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderMessagesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchaseOrderMessagesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchaseOrderMessagesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchaseOrderMessages).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
