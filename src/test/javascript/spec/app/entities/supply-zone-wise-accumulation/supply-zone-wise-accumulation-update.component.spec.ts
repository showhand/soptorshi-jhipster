/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneWiseAccumulationUpdateComponent } from 'app/entities/supply-zone-wise-accumulation/supply-zone-wise-accumulation-update.component';
import { SupplyZoneWiseAccumulationService } from 'app/entities/supply-zone-wise-accumulation/supply-zone-wise-accumulation.service';
import { SupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';

describe('Component Tests', () => {
    describe('SupplyZoneWiseAccumulation Management Update Component', () => {
        let comp: SupplyZoneWiseAccumulationUpdateComponent;
        let fixture: ComponentFixture<SupplyZoneWiseAccumulationUpdateComponent>;
        let service: SupplyZoneWiseAccumulationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneWiseAccumulationUpdateComponent]
            })
                .overrideTemplate(SupplyZoneWiseAccumulationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyZoneWiseAccumulationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneWiseAccumulationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZoneWiseAccumulation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZoneWiseAccumulation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyZoneWiseAccumulation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyZoneWiseAccumulation = entity;
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
