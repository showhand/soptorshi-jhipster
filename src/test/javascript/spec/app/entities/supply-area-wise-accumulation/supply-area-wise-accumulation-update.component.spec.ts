/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaWiseAccumulationUpdateComponent } from 'app/entities/supply-area-wise-accumulation/supply-area-wise-accumulation-update.component';
import { SupplyAreaWiseAccumulationService } from 'app/entities/supply-area-wise-accumulation/supply-area-wise-accumulation.service';
import { SupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';

describe('Component Tests', () => {
    describe('SupplyAreaWiseAccumulation Management Update Component', () => {
        let comp: SupplyAreaWiseAccumulationUpdateComponent;
        let fixture: ComponentFixture<SupplyAreaWiseAccumulationUpdateComponent>;
        let service: SupplyAreaWiseAccumulationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaWiseAccumulationUpdateComponent]
            })
                .overrideTemplate(SupplyAreaWiseAccumulationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyAreaWiseAccumulationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyAreaWiseAccumulationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyAreaWiseAccumulation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyAreaWiseAccumulation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyAreaWiseAccumulation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyAreaWiseAccumulation = entity;
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
