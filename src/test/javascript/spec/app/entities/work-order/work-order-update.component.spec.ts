/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { WorkOrderUpdateComponent } from 'app/entities/work-order/work-order-update.component';
import { WorkOrderService } from 'app/entities/work-order/work-order.service';
import { WorkOrder } from 'app/shared/model/work-order.model';

describe('Component Tests', () => {
    describe('WorkOrder Management Update Component', () => {
        let comp: WorkOrderUpdateComponent;
        let fixture: ComponentFixture<WorkOrderUpdateComponent>;
        let service: WorkOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [WorkOrderUpdateComponent]
            })
                .overrideTemplate(WorkOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WorkOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkOrderService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new WorkOrder(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.workOrder = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new WorkOrder();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.workOrder = entity;
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
