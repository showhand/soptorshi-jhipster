/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepreciationCalculationUpdateComponent } from 'app/entities/depreciation-calculation/depreciation-calculation-update.component';
import { DepreciationCalculationService } from 'app/entities/depreciation-calculation/depreciation-calculation.service';
import { DepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';

describe('Component Tests', () => {
    describe('DepreciationCalculation Management Update Component', () => {
        let comp: DepreciationCalculationUpdateComponent;
        let fixture: ComponentFixture<DepreciationCalculationUpdateComponent>;
        let service: DepreciationCalculationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepreciationCalculationUpdateComponent]
            })
                .overrideTemplate(DepreciationCalculationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepreciationCalculationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepreciationCalculationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepreciationCalculation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depreciationCalculation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepreciationCalculation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.depreciationCalculation = entity;
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
