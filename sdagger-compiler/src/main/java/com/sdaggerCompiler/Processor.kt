package com.sdaggerCompiler

import com.sdaggerAnnotations.BindModule
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.io.IOException
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter
import javax.tools.Diagnostic


class Processor : AbstractProcessor() {

    private var mProcessingEnvironment: ProcessingEnvironment? = null

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        mProcessingEnvironment = processingEnvironment
    }

    override fun process(
        annotations: Set<TypeElement>,
        roundEnvironment: RoundEnvironment
    ): Boolean {

        for (typeElement in ElementFilter.typesIn(
            roundEnvironment.getElementsAnnotatedWith(
                BindModule::class.java
            )
        )) {

            val packageName = mProcessingEnvironment!!.elementUtils.getPackageOf(typeElement)
                .qualifiedName.toString()
            val typeName = typeElement.simpleName.toString()
            val className = ClassName.get(packageName, typeName)
            val generatedClassName = ClassName.get(packageName, typeName + "Module")

            // define the wrapper class
            val classBuilder = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(dagger.Module::class.java)

            val viewModel = ClassName.get(packageName, typeName + "ViewModel")
            // add method that maps the views with id
            val bindViewsMethodBuilder = MethodSpec
                .methodBuilder("provideViewModel")
                .addAnnotation(dagger.Provides::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                    ClassName.get("org.whiteboard.di.modules.data", "DataManager"),
                    "dataManager"
                )
                .addParameter(
                    ClassName.get("io.reactivex.disposables", "CompositeDisposable"),
                    "compositeDisposable"
                )
                .addParameter(
                    ClassName.get(
                        "org.whiteboard.di.modules.data.scheduler",
                        "SchedulersFacade"
                    ), "schedulersFacade"
                )
                .returns(viewModel)
                .addStatement("return new ${viewModel.simpleName()}(dataManager,compositeDisposable,schedulersFacade)")

            classBuilder.addMethod(bindViewsMethodBuilder.build())

            // write the defines class to a java file
            try {
                JavaFile.builder(
                    "$packageName.di",
                    classBuilder.build()
                )
                    .build()
                    .writeTo(mProcessingEnvironment!!.filer)
            } catch (e: IOException) {
                mProcessingEnvironment!!.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    e.toString(),
                    typeElement
                )
            }
        }

        return true
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return object : HashSet<String>() {
            init {
                add(BindModule::class.java.canonicalName)
            }
        }
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}