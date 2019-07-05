/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { WorkOrderDetailComponent } from 'app/entities/work-order/work-order-detail.component';
import { WorkOrder } from 'app/shared/model/work-order.model';

describe('Component Tests', () => {
    describe('WorkOrder Management Detail Component', () => {
        let comp: WorkOrderDetailComponent;
        let fixture: ComponentFixture<WorkOrderDetailComponent>;
        const route = ({ data: of({ workOrder: new WorkOrder(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [WorkOrderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WorkOrderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WorkOrderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.workOrder).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
