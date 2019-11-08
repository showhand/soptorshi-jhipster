/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyOrderDetailsUpdateComponent } from 'app/entities/supply-order-details/supply-order-details-update.component';
import { SupplyOrderDetailsService } from 'app/entities/supply-order-details/supply-order-details.service';
import { SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

describe('Component Tests', () => {
    describe('SupplyOrderDetails Management Update Component', () => {
        let comp: SupplyOrderDetailsUpdateComponent;
        let fixture: ComponentFixture<SupplyOrderDetailsUpdateComponent>;
        let service: SupplyOrderDetailsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyOrderDetailsUpdateComponent]
            })
                .overrideTemplate(SupplyOrderDetailsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyOrderDetailsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyOrderDetailsService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyOrderDetails(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyOrderDetails = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyOrderDetails();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyOrderDetails = entity;
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
