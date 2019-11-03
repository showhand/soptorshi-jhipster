/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderDetailComponent } from 'app/entities/commercial-work-order/commercial-work-order-detail.component';
import { CommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';

describe('Component Tests', () => {
    describe('CommercialWorkOrder Management Detail Component', () => {
        let comp: CommercialWorkOrderDetailComponent;
        let fixture: ComponentFixture<CommercialWorkOrderDetailComponent>;
        const route = ({ data: of({ commercialWorkOrder: new CommercialWorkOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialWorkOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialWorkOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialWorkOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
