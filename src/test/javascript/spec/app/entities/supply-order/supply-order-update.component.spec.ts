/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyOrderUpdateComponent } from 'app/entities/supply-order/supply-order-update.component';
import { SupplyOrderService } from 'app/entities/supply-order/supply-order.service';
import { SupplyOrder } from 'app/shared/model/supply-order.model';

describe('Component Tests', () => {
    describe('SupplyOrder Management Update Component', () => {
        let comp: SupplyOrderUpdateComponent;
        let fixture: ComponentFixture<SupplyOrderUpdateComponent>;
        let service: SupplyOrderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyOrderUpdateComponent]
            })
                .overrideTemplate(SupplyOrderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyOrderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyOrderService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyOrder(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyOrder = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyOrder();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyOrder = entity;
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
