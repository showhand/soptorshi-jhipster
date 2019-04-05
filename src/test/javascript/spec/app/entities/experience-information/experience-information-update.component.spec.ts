/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ExperienceInformationUpdateComponent } from 'app/entities/experience-information/experience-information-update.component';
import { ExperienceInformationService } from 'app/entities/experience-information/experience-information.service';
import { ExperienceInformation } from 'app/shared/model/experience-information.model';

describe('Component Tests', () => {
    describe('ExperienceInformation Management Update Component', () => {
        let comp: ExperienceInformationUpdateComponent;
        let fixture: ComponentFixture<ExperienceInformationUpdateComponent>;
        let service: ExperienceInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ExperienceInformationUpdateComponent]
            })
                .overrideTemplate(ExperienceInformationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExperienceInformationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExperienceInformationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperienceInformation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experienceInformation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ExperienceInformation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.experienceInformation = entity;
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
