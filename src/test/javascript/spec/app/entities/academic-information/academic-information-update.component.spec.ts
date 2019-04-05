/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationUpdateComponent } from 'app/entities/academic-information/academic-information-update.component';
import { AcademicInformationService } from 'app/entities/academic-information/academic-information.service';
import { AcademicInformation } from 'app/shared/model/academic-information.model';

describe('Component Tests', () => {
    describe('AcademicInformation Management Update Component', () => {
        let comp: AcademicInformationUpdateComponent;
        let fixture: ComponentFixture<AcademicInformationUpdateComponent>;
        let service: AcademicInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationUpdateComponent]
            })
                .overrideTemplate(AcademicInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcademicInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicInformationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicInformation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicInformation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicInformation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicInformation = entity;
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
