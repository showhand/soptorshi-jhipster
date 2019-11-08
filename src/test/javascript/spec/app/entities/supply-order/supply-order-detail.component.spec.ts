/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyOrderDetailComponent } from 'app/entities/supply-order/supply-order-detail.component';
import { SupplyOrder } from 'app/shared/model/supply-order.model';

describe('Component Tests', () => {
    describe('SupplyOrder Management Detail Component', () => {
        let comp: SupplyOrderDetailComponent;
        let fixture: ComponentFixture<SupplyOrderDetailComponent>;
        const route = ({ data: of({ supplyOrder: new SupplyOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
