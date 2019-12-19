/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderUpdateComponent } from 'app/entities/commercial-work-order/commercial-work-order-update.component';
import { CommercialWorkOrderService } from 'app/entities/commercial-work-order/commercial-work-order.service';
import { CommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';

describe('Component Tests', () => {
    describe('CommercialWorkOrder Management Update Component', () => {
        let comp: CommercialWorkOrderUpdateComponent;
        let fixture: ComponentFixture<CommercialWorkOrderUpdateComponent>;
        let service: CommercialWorkOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderUpdateComponent]
            })
                .overrideTemplate(CommercialWorkOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialWorkOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialWorkOrderService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialWorkOrder(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialWorkOrder = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialWorkOrder();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialWorkOrder = entity;
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
