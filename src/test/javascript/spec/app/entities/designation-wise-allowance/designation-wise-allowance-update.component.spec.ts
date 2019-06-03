/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DesignationWiseAllowanceUpdateComponent } from 'app/entities/designation-wise-allowance/designation-wise-allowance-update.component';
import { DesignationWiseAllowanceService } from 'app/entities/designation-wise-allowance/designation-wise-allowance.service';
import { DesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';

describe('Component Tests', () => {
    describe('DesignationWiseAllowance Management Update Component', () => {
        let comp: DesignationWiseAllowanceUpdateComponent;
        let fixture: ComponentFixture<DesignationWiseAllowanceUpdateComponent>;
        let service: DesignationWiseAllowanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DesignationWiseAllowanceUpdateComponent]
            })
                .overrideTemplate(DesignationWiseAllowanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DesignationWiseAllowanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignationWiseAllowanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DesignationWiseAllowance(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.designationWiseAllowance = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DesignationWiseAllowance();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.designationWiseAllowance = entity;
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
