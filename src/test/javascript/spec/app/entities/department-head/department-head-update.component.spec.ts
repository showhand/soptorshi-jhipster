/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DepartmentHeadUpdateComponent } from 'app/entities/department-head/department-head-update.component';
import { DepartmentHeadService } from 'app/entities/department-head/department-head.service';
import { DepartmentHead } from 'app/shared/model/department-head.model';

describe('Component Tests', () => {
    describe('DepartmentHead Management Update Component', () => {
        let comp: DepartmentHeadUpdateComponent;
        let fixture: ComponentFixture<DepartmentHeadUpdateComponent>;
        let service: DepartmentHeadService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepartmentHeadUpdateComponent]
            })
                .overrideTemplate(DepartmentHeadUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepartmentHeadUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepartmentHeadService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepartmentHead(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.departmentHead = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DepartmentHead();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.departmentHead = entity;
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
